package com.junior.osservice.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junior.osservice.domain.model.Cliente;

@RestController
public class ClienteController {
	
	@GetMapping("/clientes")
	public List<Cliente> listar() {
		var cliente1 = new Cliente();
		cliente1.setId(1L);
		cliente1.setNome("João das Contas");
		cliente1.setTelefone("99999999");
		cliente1.setEmail("joao@gmail");
		
		var cliente2 = new Cliente();
		cliente2.setId(1L);
		cliente2.setNome("Maria");
		cliente2.setTelefone("99999999");
		cliente2.setEmail("maria@gmail");
		
		return Arrays.asList(cliente1, cliente2);
	}
}
