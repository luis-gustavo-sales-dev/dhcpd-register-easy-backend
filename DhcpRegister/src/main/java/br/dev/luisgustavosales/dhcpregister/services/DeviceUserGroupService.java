package br.dev.luisgustavosales.dhcpregister.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.luisgustavosales.dhcpregister.entities.DeviceUserGroup;
import br.dev.luisgustavosales.dhcpregister.exceptionhandler.exceptions.DeviceUserGroupNotFoundException;
import br.dev.luisgustavosales.dhcpregister.repositories.DeviceUserGroupRepository;

@Service
public class DeviceUserGroupService {
	
	@Autowired
	private DeviceUserGroupRepository deviceUserGroupRepository;
	
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
}
