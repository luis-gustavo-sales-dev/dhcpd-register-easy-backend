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
			// System.out.println("range: " + range);
			var deviceUserGroupWithIPRange = ipRangeGroupRepository.findByRange(range.getRange());
			deviceUserGroupWithIPRange.ifPresent(
					s -> { 
							throw new IpRangeAlreadyExistsInOtherDeviceUserGroupException("Esse range já está cadastrado: " + range); 
						}
					);
			// System.out.println("isPresent: " + deviceUserGroupWithIPRange.isPresent());
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

	public DeviceUserGroup update(DeviceUserGroup deviceUserGroupToUpdate, DeviceUserGroup deviceUserGroupOnDB) {
		// TODO Auto-generated method stub
		
		/*
		 * Verificar se o nome que estou tentando atualizar não existe para outro grupo que não é o que 
		 * já havia sido recuperado.
		 * */
		
		this.deviceUserGroupRepository.findByNameIgnoreCase(deviceUserGroupToUpdate.getName())
				.ifPresent( (c) -> {
					if (c.getId() != deviceUserGroupOnDB.getId()) {
						throw new DeviceUserGroupAlreadyExistsException("Já existe outro grupo com esse nome: " + deviceUserGroupToUpdate.getName());						
					}
				});
		
		
		/* Verifica se os range do grupo recebido para ser atualizado
		 * estão no Grupo do banco. 
		 * 
		 * Se sim, quer dizer não é necessário verificar
		 * se o range está em uso por outro grupo pois o range já pertence ao grupo.
		 * 
		 * Se não, tem que procurar se que não está no Grupo do banco
		 * pode ser usado. */
		
		System.out.println("deviceUserGroupToUpdate: " + deviceUserGroupToUpdate);
		
		System.out.println("deviceUserGroupOnDB: " + deviceUserGroupOnDB);
		
		var allMatch = true;
		List<IpRangeGroup> listOfRangeIsNotIndeviceUserGroupOnDB = new ArrayList<>();
		
		if (deviceUserGroupToUpdate.getIprangegroup().size() > 0) {

			for (IpRangeGroup iprg : deviceUserGroupToUpdate.getIprangegroup()) {
				var isRangeInGroupDB = deviceUserGroupOnDB.getIprangegroup().contains(iprg);
				
				System.out.println("isRangeInGroupDB: " + isRangeInGroupDB);
				// O range atual não pertence ao grupo no banco
				if (!isRangeInGroupDB) {
					listOfRangeIsNotIndeviceUserGroupOnDB.add(iprg);
					if (allMatch) {
						allMatch =  false;						
					}
				}
			}

		}
		
		
		/*
		 * Um ou mais ranges não pertencem ao grupo do banco então precisa ser verificado se já não pertence a outro grupo.
		 * */
		if (!allMatch) {
	
			listOfRangeIsNotIndeviceUserGroupOnDB.forEach( (iprangeout ) -> {
				ipRangeGroupRepository.findByRange(iprangeout.getRange())
					.ifPresent( (i) -> {
						throw new IpRangeAlreadyExistsInOtherDeviceUserGroupException(
								"O range " + i.getRange() + " já pertence a outro grupo.");
					});
			});

		}
		
		deviceUserGroupToUpdate.setId(deviceUserGroupOnDB.getId());
		return this.deviceUserGroupRepository.save(deviceUserGroupToUpdate);
		
	}

	public List<DeviceUserGroup> findAll() {
		// TODO Auto-generated method stub
		return this.deviceUserGroupRepository.findAll();
	}
}
