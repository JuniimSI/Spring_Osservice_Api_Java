package com.junior.osservice.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.junior.osservice.domain.exception.EntidadeNaoEncontradaException;
import com.junior.osservice.domain.exception.NegocioException;
import com.junior.osservice.domain.model.Cliente;
import com.junior.osservice.domain.model.Comentario;
import com.junior.osservice.domain.model.OrdemServico;
import com.junior.osservice.domain.model.StatusOrdemServico;
import com.junior.osservice.domain.repository.ClienteRepository;
import com.junior.osservice.domain.repository.ComentarioRepository;
import com.junior.osservice.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	private OrdemServicoRepository osRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public OrdemServico criar(OrdemServico os) {
		Cliente cliente = clienteRepository.findById(os.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
			
		os.setCliente(cliente);		
		os.setStatus(StatusOrdemServico.ABERTA);
		os.setDataAbertura(OffsetDateTime.now());
		return osRepository.save(os);
	}
	
	
	public void finalizar(Long osId) {
		OrdemServico os = buscar(osId);
		os.finalizar();
        osRepository.save(os);
	}	
	
	public Comentario adicionarComentario(Long osId, String desc) {
		OrdemServico os = buscar(osId);
		Comentario c = new Comentario();
		c.setDataEnvio(OffsetDateTime.now());
		c.setDescricao(desc);
		c.setOrdemServico(os);
		return comentarioRepository.save(c);
	}
	
	
	private OrdemServico buscar(Long osId) {
		return osRepository.findById(osId).orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de Serviço não encontrado"));
	}
}
