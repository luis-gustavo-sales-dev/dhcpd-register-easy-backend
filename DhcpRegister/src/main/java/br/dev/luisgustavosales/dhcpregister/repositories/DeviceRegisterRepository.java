package br.dev.luisgustavosales.dhcpregister.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.dev.luisgustavosales.dhcpregister.entities.DeviceRegister;

@Repository
public interface DeviceRegisterRepository extends JpaRepository<DeviceRegister, Long> {

	Optional<DeviceRegister> findById(Long id);
}
