package br.dev.luisgustavosales.dhcpregister.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.luisgustavosales.dhcpregister.dtos.BulkCreateDeviceRegisterDTO;
import br.dev.luisgustavosales.dhcpregister.entities.DeviceRegister;
import br.dev.luisgustavosales.dhcpregister.entities.DeviceUserGroup;
import br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions.CpfAndMacAlreadyExistsException;
import br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions.CpfAndMacNotFoundException;
import br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions.DeviceTypeNotFoundException;
import br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions.DeviceUserGroupNotFoundException;
import br.dev.luisgustavosales.dhcpregister.repositories.DeviceRegisterRepository;
import br.dev.luisgustavosales.dhcpregister.repositories.DeviceTypeRepository;
import br.dev.luisgustavosales.dhcpregister.repositories.DeviceUserGroupRepository;

@Service
public class DeviceRegisterService {

	@Autowired
	private DeviceRegisterRepository deviceRegisterRepository;
	
	@Autowired
	private DeviceUserGroupRepository deviceUserGroupRepository;
	
	@Autowired
	private DeviceTypeRepository deviceTypeRepository;

	public DeviceRegister findByCpfAndMac(String cpf, String mac) {
		
		var deviceRegister = deviceRegisterRepository.findByIdsCpfAndIdsMac(cpf, mac);
		
		return deviceRegister.orElseThrow( 
				() -> new CpfAndMacNotFoundException("Cpf " + cpf + 
						" e mac " + mac + " não foram encontrados."));
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
		
		var deviceRegisterAlreadyExists = deviceRegisterRepository
				.findByIdsCpfAndIdsMac(cpf, mac);
		
		deviceRegisterAlreadyExists.ifPresent( s -> { 
				throw new CpfAndMacAlreadyExistsException("Este registro com cpf " + cpf +
						" e mac " + mac + " já existe!");
			});
		
		// Precisa verificar se o grupo é válido antes de criar
		
		deviceUserGroupRepository.findById(deviceRegister.getGroup().getId())
			.orElseThrow( 
					() -> new DeviceUserGroupNotFoundException("Não há nenhum " +
							"grupo associado a esse id: " + 
							deviceRegister.getGroup().getId()));
		
		deviceTypeRepository.findById(deviceRegister.getDeviceType().getId())
			.orElseThrow(
					() -> new DeviceTypeNotFoundException("Não há nenhum " +
							"tipo de dispositivo associado a esse id: " + 
							deviceRegister.getDeviceType().getId()));
			
		return this.deviceRegisterRepository.save(deviceRegister);
	}
	
	@Transactional
	public List<DeviceRegister> createBulk(BulkCreateDeviceRegisterDTO bulkCreateDeviceRegisterDTO) {
		
		List<DeviceRegister> listOfDeviceRegisterToSave = new ArrayList<DeviceRegister>();
		
		// Verifique se já existe algum cadastro com a chave composta de cpf e mac
		bulkCreateDeviceRegisterDTO.getMacs().stream().forEach( mac -> {
			var deviceRegisterAlreadyExists = deviceRegisterRepository
					.findByIdsCpfAndIdsMac(bulkCreateDeviceRegisterDTO.getCpf(), mac);
			
			deviceRegisterAlreadyExists.ifPresent( s -> { 
					throw new CpfAndMacAlreadyExistsException("Este registro com cpf " + 
								bulkCreateDeviceRegisterDTO.getCpf() +
								" e mac " + mac + " já existe!");
				});
			
			
			listOfDeviceRegisterToSave.add(
					new DeviceRegister(
							bulkCreateDeviceRegisterDTO.getCpf(),
							mac,
							bulkCreateDeviceRegisterDTO.getGroup(),
							bulkCreateDeviceRegisterDTO.getDeviceType()
						));
			
			
		});
		
		
		
		// Precisa verificar se o grupo é válido antes de criar
		
		deviceUserGroupRepository.findById(bulkCreateDeviceRegisterDTO.getGroup().getId())
			.orElseThrow( 
					() -> new DeviceUserGroupNotFoundException("Não há nenhum " +
							"grupo associado a esse id: " + 
							bulkCreateDeviceRegisterDTO.getGroup().getId()));
		
		// Precisa verificar se o tipo de dispositivo é válido antes de criar
		
		deviceTypeRepository.findById(bulkCreateDeviceRegisterDTO.getDeviceType().getId())
			.orElseThrow(
					() -> new DeviceTypeNotFoundException("Não há nenhum " +
							"tipo de dispositivo associado a esse id: " + 
							bulkCreateDeviceRegisterDTO.getDeviceType().getId()));
		
		// Salva a lista de Dispositivos vindos do bulkCreateDeviceRegisterDTO
		
			
		return this.deviceRegisterRepository.saveAll(listOfDeviceRegisterToSave);
	}
	
	public DeviceRegister update(
			String cpf, 
			String mac, 
			DeviceRegister deviceRegisterToUpdate) {
		
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
							"grupo associado a esse id: " + 
							deviceRegisterToUpdate.getGroup().getId()));
		
		// Existe outro registro com o CPF e MAC informados para atualização
		deviceRegisterRepository.findByIdsCpfAndIdsMac(
				deviceRegisterToUpdate.getIds().getCpf(), 
				deviceRegisterToUpdate.getIds().getMac())
					.ifPresent( 
							(d) ->  {
									throw new CpfAndMacAlreadyExistsException(
										"Já existe um registro com o Cpf " + 
												deviceRegisterToUpdate.getIds().getCpf() + 
												" e Mac " + 
												deviceRegisterToUpdate.getIds().getMac() + 
												" informados para atualização.");
								});
		
		deviceTypeRepository.findById(deviceRegisterToUpdate.getDeviceType().getId())
		.orElseThrow(
				() -> new DeviceTypeNotFoundException("Não há nenhum " +
						"tipo de dispositivo associado a esse id: " + 
						deviceRegisterToUpdate.getDeviceType().getId()));
		
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
