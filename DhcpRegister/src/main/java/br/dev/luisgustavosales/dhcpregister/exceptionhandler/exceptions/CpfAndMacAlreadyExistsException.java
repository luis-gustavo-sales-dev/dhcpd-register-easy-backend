package br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions;

public class CpfAndMacAlreadyExistsException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public CpfAndMacAlreadyExistsException(String message) {
		super(message);
	}

}
