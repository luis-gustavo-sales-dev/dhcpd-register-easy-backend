package br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions;

public class DeviceUserGroupAreUsedByDeviceRegiterException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public DeviceUserGroupAreUsedByDeviceRegiterException(String message) {
		super(message);
	}

}
