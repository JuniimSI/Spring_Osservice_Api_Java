package com.junior.osservice.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.junior.osservice.api.model.OrdemServicoInput;
import com.junior.osservice.api.model.OrdemServicoModel;
import com.junior.osservice.domain.model.OrdemServico;
import com.junior.osservice.domain.repository.OrdemServicoRepository;
import com.junior.osservice.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {
	
	@Autowired
	private GestaoOrdemServicoService gosService;
	
	@Autowired
	private OrdemServicoRepository osRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoModel criar(@Valid @RequestBody OrdemServicoInput osi) {
		OrdemServico os = toEntity(osi);
		return toModel(gosService.criar(os));
	}
	
	@GetMapping
	public List<OrdemServicoModel> listar(){
		return toCollectionModel(osRepository.findAll());
	}

	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long ordemServicoId) {
		Optional<OrdemServico> ordemServico = osRepository.findById(ordemServicoId);
		if(ordemServico.isPresent()) {
			OrdemServicoModel model = toModel(ordemServico.get());
			return ResponseEntity.ok(model);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{ordemServicoId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long ordemServicoId ) {
		gosService.finalizar(ordemServicoId);
	}
	
	
	private OrdemServicoModel toModel(OrdemServico os) {
		return modelMapper.map(os, OrdemServicoModel.class);
	}
	
	private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> los){
		return los.stream()
				.map(ordemServico -> toModel(ordemServico))
				.collect(Collectors.toList());
	}
	
	private OrdemServico toEntity(OrdemServicoInput osi) {
		return modelMapper.map(osi, OrdemServico.class);
	}
}
