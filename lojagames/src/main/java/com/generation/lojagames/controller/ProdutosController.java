package com.generation.lojagames.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.lojagames.model.ProdutosGames;
import com.generation.lojagames.repository.CategoriaRepository;
import com.generation.lojagames.repository.ProdutosRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/produtos-game")
@CrossOrigin (origins = "*", allowedHeaders = "*")
public class ProdutosController {
	
	@Autowired
	private ProdutosRepository produtosRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<ProdutosGames>> getAll(){
		return ResponseEntity.ok(produtosRepository.findAll());
	}
	
	@GetMapping ("/{id}")
	public ResponseEntity<ProdutosGames> getById(@PathVariable Long id){
		return produtosRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@PostMapping
	public ResponseEntity<ProdutosGames> post(@Valid @RequestBody ProdutosGames produtosgames){
		if (categoriaRepository.existsById(produtosgames.getCategoria().getId()))
			return ResponseEntity.status(HttpStatus.CREATED)
				.body(produtosRepository.save(produtosgames));
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!", null);	
	}
	
	@PutMapping
	public ResponseEntity<ProdutosGames> put (@Valid @RequestBody ProdutosGames produtosgames){
		return ResponseEntity.status(HttpStatus.OK)
				.body(produtosRepository.save(produtosgames));
	}
	
	@DeleteMapping ("/{id}")
	public void delete (@PathVariable Long id){
		Optional<ProdutosGames> produtosgames = produtosRepository.findById(id);
		
			if(produtosgames.isEmpty())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			
			produtosRepository.deleteById(id);
	}
}
