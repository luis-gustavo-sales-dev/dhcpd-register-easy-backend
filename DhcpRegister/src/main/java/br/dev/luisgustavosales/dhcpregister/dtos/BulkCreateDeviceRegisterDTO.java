package br.dev.luisgustavosales.dhcpregister.dtos;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.dev.luisgustavosales.dhcpregister.entities.DeviceType;
import br.dev.luisgustavosales.dhcpregister.entities.DeviceUserGroup;
import lombok.Data;

@Data
public class BulkCreateDeviceRegisterDTO {
	
	@NotBlank
	private String cpf;
	
	@Size(min=1, max=4)
	private Set<String> macs;
	
	@NotNull
	private DeviceUserGroup group;
	
	@NotNull
	private DeviceType deviceType;

}
