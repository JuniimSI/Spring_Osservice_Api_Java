package com.junior.osservice.domain.exception;

public class EntidadeNaoEncontradaException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public EntidadeNaoEncontradaException(String menssagem) {
		super(menssagem);
	}

}
