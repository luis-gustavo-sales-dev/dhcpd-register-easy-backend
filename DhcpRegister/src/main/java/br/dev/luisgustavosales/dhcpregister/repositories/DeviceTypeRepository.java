package br.dev.luisgustavosales.dhcpregister.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.luisgustavosales.dhcpregister.entities.DeviceType;

public interface DeviceTypeRepository extends JpaRepository<DeviceType, Long> {
	Optional<DeviceType> findById(Long id);
}
