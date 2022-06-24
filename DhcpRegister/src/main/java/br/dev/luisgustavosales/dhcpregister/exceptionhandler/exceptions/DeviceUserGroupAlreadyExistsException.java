package br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions;

public class DeviceUserGroupAlreadyExistsException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public DeviceUserGroupAlreadyExistsException(String message) {
		super(message);
	}

}
