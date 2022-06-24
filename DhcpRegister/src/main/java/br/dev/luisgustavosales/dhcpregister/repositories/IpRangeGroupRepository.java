package br.dev.luisgustavosales.dhcpregister.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.luisgustavosales.dhcpregister.entities.IpRangeGroup;

public interface IpRangeGroupRepository extends JpaRepository<IpRangeGroup, Long> {
	Optional<IpRangeGroup> findByRange(String range);
}
