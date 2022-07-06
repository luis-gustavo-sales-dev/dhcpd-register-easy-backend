package br.dev.luisgustavosales.dhcpregister.dtos;

import java.util.Set;

import br.dev.luisgustavosales.dhcpregister.entities.DeviceRegisterPK;
import br.dev.luisgustavosales.dhcpregister.entities.DeviceType;
import br.dev.luisgustavosales.dhcpregister.entities.DeviceUserGroup;
import lombok.Data;

@Data
public class BulkCreateDeviceRegisterDTO {
	
	private Set<DeviceRegisterPK> ids;
	
	private DeviceUserGroup group;
	
	private DeviceType deviceType;

}
