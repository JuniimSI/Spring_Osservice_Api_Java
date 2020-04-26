package com.junior.osservice.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.junior.osservice.domain.model.Cliente;
import com.junior.osservice.domain.repository.ClienteRepository;
import com.junior.osservice.domain.service.CadastroClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}
	
	@Autowired
	private CadastroClienteService cadastroCliente;
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		if(cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return cadastroCliente.salvar(cliente);		
	} 
	
	@PutMapping("/{clientId}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clientId, @RequestBody Cliente cliente) {
		if(!clienteRepository.existsById(clientId)) {
			return ResponseEntity.notFound().build();
		}
		cliente.setId(clientId);
		cliente = cadastroCliente.salvar(cliente);
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/clientId")
	public ResponseEntity<Void> remover(@PathVariable Long clientId) {
		if(!clienteRepository.existsById(clientId)) {
			return ResponseEntity.notFound().build();
		}
		cadastroCliente.excluir(clientId);
		return ResponseEntity.noContent().build();
	}
}
