package br.dev.luisgustavosales.dhcpregister.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.luisgustavosales.dhcpregister.entities.DeviceRegister;
import br.dev.luisgustavosales.dhcpregister.repositories.DeviceRegisterRepository;

@Service
public class DeviceRegisterService {

	@Autowired
	private DeviceRegisterRepository deviceRegisterRepository;
	
	public DeviceRegister create(DeviceRegister deviceRegister) {
		return this.deviceRegisterRepository.save(deviceRegister);
	}

	public DeviceRegister findByCpfAndMac(String cpf, String mac) {
		
		var deviceRegister = deviceRegisterRepository.findByIdsCpfAndIdsMac(cpf, mac);
		
		if (deviceRegister.isEmpty()) {
			return null;
		}
		return deviceRegister.get();
	}
	
	public List<DeviceRegister> findByCpf(String cpf) {
		
		var deviceRegister = deviceRegisterRepository.findByIdsCpfContaining(cpf);
		
		if (deviceRegister.isEmpty()) {
			return null;
		}
		return deviceRegister.get();
	}
}
