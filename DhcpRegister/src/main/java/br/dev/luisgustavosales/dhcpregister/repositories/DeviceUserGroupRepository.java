package br.dev.luisgustavosales.dhcpregister.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.luisgustavosales.dhcpregister.entities.DeviceUserGroup;

public interface DeviceUserGroupRepository extends JpaRepository<DeviceUserGroup, Long>{
	
	Optional<DeviceUserGroup> findById(Long id);
	Optional<DeviceUserGroup> findByIprangegroupRange(String range);
	Optional<List<DeviceUserGroup>> findByNameIgnoreCaseContaining(String name);
	
}
