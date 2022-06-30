package br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions;

public class DeviceTypeNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public DeviceTypeNotFoundException(String message) {
		super(message);
	}

}
