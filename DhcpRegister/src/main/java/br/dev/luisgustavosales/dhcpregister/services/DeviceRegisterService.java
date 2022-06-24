package br.dev.luisgustavosales.dhcpregister.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.luisgustavosales.dhcpregister.entities.DeviceRegister;
import br.dev.luisgustavosales.dhcpregister.entities.DeviceUserGroup;
import br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions.CpfAndMacAlreadyExistsException;
import br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions.CpfAndMacNotFoundException;
import br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions.DeviceUserGroupNotFoundException;
import br.dev.luisgustavosales.dhcpregister.repositories.DeviceRegisterRepository;
import br.dev.luisgustavosales.dhcpregister.repositories.DeviceUserGroupRepository;

@Service
public class DeviceRegisterService {

	@Autowired
	private DeviceRegisterRepository deviceRegisterRepository;
	
	@Autowired
	private DeviceUserGroupRepository deviceUserGroupRepository;

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
	
	public DeviceRegister create(DeviceRegister deviceRegister) {
		var cpf = deviceRegister.getIds().getCpf();
		var mac = deviceRegister.getIds().getMac();
		
		var deviceRegisterAlreadyExists = deviceRegisterRepository.findByIdsCpfAndIdsMac(cpf, mac);
		
		deviceRegisterAlreadyExists.ifPresent( s -> { 
				throw new CpfAndMacAlreadyExistsException("Este registro com cpf " + cpf +
						" e mac " + mac + " já existe!");
			});
		
		// Precisa verificar se o grupo é válido antes de criar
		
		deviceUserGroupRepository.findById(deviceRegister.getGroup().getId())
			.orElseThrow( 
					() -> new DeviceUserGroupNotFoundException("Não há nenhum " +
							"grupo associado a esse id: " + deviceRegister.getGroup().getId()));
		
		return this.deviceRegisterRepository.save(deviceRegister);
	}
	
	public DeviceRegister update(String cpf, String mac, DeviceRegister deviceRegisterToUpdate) {
		
		// Existe um registro com o mac e cpf informados?
		var dr = deviceRegisterRepository.findByIdsCpfAndIdsMac(cpf, mac)
				.orElseThrow( 
						() -> new CpfAndMacNotFoundException(
								"Não há registro de dispositivo com Cpf " + 
										cpf + " e Mac " + mac + "!."));
		
		// O grupo informado é válido?
		deviceUserGroupRepository.findById(deviceRegisterToUpdate.getGroup().getId())
			.orElseThrow( 
					() -> new DeviceUserGroupNotFoundException("Não há nenhum " +
							"grupo associado a esse id: " + deviceRegisterToUpdate.getGroup().getId()));
		
		// Existe outro registro com o CPF e MAC informados para atualização
		deviceRegisterRepository.findByIdsCpfAndIdsMac(
				deviceRegisterToUpdate.getIds().getCpf(), 
				deviceRegisterToUpdate.getIds().getMac())
					.ifPresent( 
							(d) ->  {
									throw new CpfAndMacAlreadyExistsException(
										"Já existe um registro com o Cpf " + 
												deviceRegisterToUpdate.getIds().getCpf() + " e Mac " + 
												deviceRegisterToUpdate.getIds().getMac() + 
												" informados para atualização.");
								});
		
		// Apague o registro anterior e crie um novo
		this.deviceRegisterRepository.delete(dr);
		
		// Cria um novo registro com os dados informados.
		return this.deviceRegisterRepository.save(deviceRegisterToUpdate);
		
	}
	
	public void deleteDeviceRegisterByCpfAndMac(String cpf, String mac) {
		var deviceRegister = deviceRegisterRepository.findByIdsCpfAndIdsMac(cpf, mac)
				.orElseThrow( 
						() -> new CpfAndMacNotFoundException(
								"Cpf " + cpf + " e mac " + mac + " não foram encontrados."));
		this.deviceRegisterRepository.delete(deviceRegister);
		
		
	}
}
