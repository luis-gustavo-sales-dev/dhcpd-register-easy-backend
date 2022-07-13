package br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions;

public class MacIsNotValidException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public MacIsNotValidException(String message) {
		super(message);
	}

}
