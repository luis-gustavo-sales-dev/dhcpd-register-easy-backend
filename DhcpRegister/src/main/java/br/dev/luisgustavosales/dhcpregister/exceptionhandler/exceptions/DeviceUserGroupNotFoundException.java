package br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions;

public class DeviceUserGroupNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public DeviceUserGroupNotFoundException(String message) {
		super(message);
	}

}
