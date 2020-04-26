package com.junior.osservice.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.junior.osservice.api.model.ComentarioInput;
import com.junior.osservice.api.model.ComentarioModel;
import com.junior.osservice.domain.exception.EntidadeNaoEncontradaException;
import com.junior.osservice.domain.model.Comentario;
import com.junior.osservice.domain.model.OrdemServico;
import com.junior.osservice.domain.repository.OrdemServicoRepository;
import com.junior.osservice.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {

	@Autowired
	private GestaoOrdemServicoService goss;
	@Autowired
	private OrdemServicoRepository osRepository;
	@Autowired
	private ModelMapper mm;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioModel adicionar(@PathVariable Long ordemServicoId,@RequestBody @Valid ComentarioInput ci) {
		Comentario c = goss.adicionarComentario(ordemServicoId, ci.getDescricao());
		return toModel(c);
	}
	
	@GetMapping
	public List<ComentarioModel> listar(@PathVariable Long ordemServicoId){
		OrdemServico os = osRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
		return toCollectionModel(os.getComentarios());
	}
	
	public List<ComentarioModel> toCollectionModel(List<Comentario> comentarios){
		return comentarios.stream().map(comentario -> toModel(comentario)).collect(Collectors.toList());
	}
	public ComentarioModel toModel(Comentario c) {
		return mm.map(c, ComentarioModel.class);
	}
}
