package com.junior.osservice.domain.exception;

public class NegocioException extends RuntimeException{
	private static final long serialVersionUID=1L;
	
	public NegocioException(String menssagem) {
		super(menssagem);
	}
	

}
