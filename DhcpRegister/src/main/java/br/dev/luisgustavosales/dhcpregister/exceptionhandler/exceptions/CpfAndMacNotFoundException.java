package br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions;

public class CpfAndMacNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public CpfAndMacNotFoundException(String message) {
		super(message);
	}

}
