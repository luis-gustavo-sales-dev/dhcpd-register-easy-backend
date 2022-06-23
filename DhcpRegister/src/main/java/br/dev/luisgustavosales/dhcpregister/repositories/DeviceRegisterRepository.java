package br.dev.luisgustavosales.dhcpregister.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.dev.luisgustavosales.dhcpregister.entities.DeviceRegister;
import br.dev.luisgustavosales.dhcpregister.entities.DeviceRegisterPK;
import br.dev.luisgustavosales.dhcpregister.entities.DeviceUserGroup;

@Repository
public interface DeviceRegisterRepository extends JpaRepository<DeviceRegister, DeviceRegisterPK> {

	// Optional<DeviceRegister> findById(Long id);
	Optional<DeviceRegister> findByIdsCpfAndIdsMac(String cpf, String mac);
	Optional<List<DeviceRegister>> findByIdsCpfContaining(String cpf);
	Optional<DeviceRegister> findByGroup(DeviceUserGroup deviceUserGroup);
}
