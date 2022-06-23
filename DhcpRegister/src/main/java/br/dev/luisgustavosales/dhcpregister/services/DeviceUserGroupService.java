package br.dev.luisgustavosales.dhcpregister.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.luisgustavosales.dhcpregister.entities.DeviceUserGroup;
import br.dev.luisgustavosales.dhcpregister.entities.IpRangeGroup;
import br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions.DeviceUserGroupAlreadyExistsException;
import br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions.DeviceUserGroupAreUsedByDeviceRegiterException;
import br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions.DeviceUserGroupNotFoundException;
import br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions.IpRangeAlreadyExistsInOtherDeviceUserGroupException;
import br.dev.luisgustavosales.dhcpregister.repositories.DeviceUserGroupRepository;
import br.dev.luisgustavosales.dhcpregister.repositories.IpRangeGroupRepository;

@Service
public class DeviceUserGroupService {
	
	@Autowired
	private DeviceUserGroupRepository deviceUserGroupRepository;
	
	@Autowired
	private IpRangeGroupRepository ipRangeGroupRepository;
	
	@Autowired
	private DeviceRegisterService deviceRegisterService;
	
	public DeviceUserGroup findById(Long id) {
		return deviceUserGroupRepository.findById(id)
					.orElseThrow( 
							() -> new DeviceUserGroupNotFoundException("Não há nenhum " +
									"grupo associado a esse id: " + id));
	}
	
	public List<DeviceUserGroup> findByName(String name) {
		return deviceUserGroupRepository
				.findByNameIgnoreCaseContaining(name)
				.orElse(new ArrayList<DeviceUserGroup>());
	}
	
	public DeviceUserGroup findByIprangegroupRange(String range) {
		return deviceUserGroupRepository.findByIprangegroupRange(range)
				.orElse(null);
	}
	
	public DeviceUserGroup create(DeviceUserGroup deviceUserGroup) {
		// Verifique se já existe um DeviceUserGroup com o mesmo nome
		var deviceUserGroupExists = deviceUserGroupRepository.findByNameIgnoreCase(deviceUserGroup.getName());
		deviceUserGroupExists.ifPresent( (s) -> {
			throw new DeviceUserGroupAlreadyExistsException("Já existe um grupo com o nome de " + deviceUserGroup.getName());
		});
		// Verifique se não existe um iprange já cadastrado
		// Não pode existir o mesmo range para dois grupos diferentes
		for (IpRangeGroup range: deviceUserGroup.getIprangegroup()) {
			System.out.println("range: " + range);
			var deviceUserGroupWithIPRange = ipRangeGroupRepository.findByRange(range.getRange());
			deviceUserGroupWithIPRange.ifPresent(
					s -> { 
							throw new IpRangeAlreadyExistsInOtherDeviceUserGroupException("Esse range já está cadastrado: " + range); 
						}
					);
			System.out.println("isPresent: " + deviceUserGroupWithIPRange.isPresent());
		}
		
		return deviceUserGroupRepository.save(deviceUserGroup);
	}
	
	public void delete(Long id) {
		// Verificar se existe um grupo com esse id
		var du = deviceUserGroupRepository.findById(id)
					.orElseThrow( 
						() -> new DeviceUserGroupNotFoundException("Não há nenhum " +
								"grupo associado a esse id: " + id));
		
		// Verificar se o grupo está associado a um DeviceRegister
		var dr = deviceRegisterService.findByDeviceUserGroup(du);
		if (dr != null) {
			throw new DeviceUserGroupAreUsedByDeviceRegiterException(
					"Este grupo está em uso pelo cadastro portador do CPF " + 
					dr.getIds().getCpf() + "e não pode ser apagado: " + id);
		}
		
		this.deviceUserGroupRepository.deleteById(id);
		
		
	}
}
