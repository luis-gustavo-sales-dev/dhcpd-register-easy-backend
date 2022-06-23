package br.dev.luisgustavosales.dhcpregister.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.luisgustavosales.dhcpregister.entities.DeviceRegister;
import br.dev.luisgustavosales.dhcpregister.repositories.DeviceRegisterRepository;

@Service
public class DeviceRegisterService {

	@Autowired
	private DeviceRegisterRepository deviceRegisterRepository;
	
	public DeviceRegister findById(Long id) {
		var deviceRegister = deviceRegisterRepository.findById(id);
		if (deviceRegister.isEmpty()) {
			return null;
		}
		return deviceRegister.get();
	}
	
	public DeviceRegister create(DeviceRegister deviceRegister) {
		return this.deviceRegisterRepository.save(deviceRegister);
	}
}
