package br.dev.luisgustavosales.dhcpregister.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.luisgustavosales.dhcpregister.entities.DeviceRegister;
import br.dev.luisgustavosales.dhcpregister.entities.DeviceUserGroup;
import br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions.CpfAndMacAlreadyExistsException;
import br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions.CpfAndMacNotFoundException;
import br.dev.luisgustavosales.dhcpregister.repositories.DeviceRegisterRepository;

@Service
public class DeviceRegisterService {

	@Autowired
	private DeviceRegisterRepository deviceRegisterRepository;
	
	public DeviceRegister create(DeviceRegister deviceRegister) {
		var cpf = deviceRegister.getIds().getCpf();
		var mac = deviceRegister.getIds().getMac();
		
		var deviceRegisterAlreadyExists = deviceRegisterRepository.findByIdsCpfAndIdsMac(cpf, mac);
		
		deviceRegisterAlreadyExists.ifPresent( s -> { 
				throw new CpfAndMacAlreadyExistsException("Este registro com cpf " + cpf +
						" e mac " + mac + " já existe!");
			});
		
		// Precisa verificar se o grupo é válido antes de criar
		
		return this.deviceRegisterRepository.save(deviceRegister);
	}

	public DeviceRegister findByCpfAndMac(String cpf, String mac) {
		
		var deviceRegister = deviceRegisterRepository.findByIdsCpfAndIdsMac(cpf, mac);
		
		return deviceRegister.orElseThrow( 
				() -> new CpfAndMacNotFoundException("Cpf " + cpf + " e mac " + mac + " não foram encontrados."));
	}
	
	public List<DeviceRegister> findByCpf(String cpf) {
		
		var deviceRegister = deviceRegisterRepository.findByIdsCpfContaining(cpf);
		
		if (deviceRegister.isEmpty()) {
			return null;
		}
		return deviceRegister.get();
	}
	
	public DeviceRegister findByDeviceUserGroup(DeviceUserGroup deviceUserGroup) {
		var dr = deviceRegisterRepository.findByGroup(deviceUserGroup);
		if (dr.isEmpty()) {
			return null;
		}
		return dr.get();
	}
	
	public void deleteDeviceRegisterByCpfAndMac(String cpf, String mac) {
		var deviceRegister = deviceRegisterRepository.findByIdsCpfAndIdsMac(cpf, mac)
				.orElseThrow( 
						() -> new CpfAndMacNotFoundException(
								"Cpf " + cpf + " e mac " + mac + " não foram encontrados."));
		this.deviceRegisterRepository.delete(deviceRegister);
		
		
	}
}
