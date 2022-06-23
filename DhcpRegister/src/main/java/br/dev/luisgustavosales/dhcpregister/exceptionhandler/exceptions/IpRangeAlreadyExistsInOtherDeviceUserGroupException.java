package br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions;

public class IpRangeAlreadyExistsInOtherDeviceUserGroupException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public IpRangeAlreadyExistsInOtherDeviceUserGroupException(String message) {
		super(message);
	}

}
