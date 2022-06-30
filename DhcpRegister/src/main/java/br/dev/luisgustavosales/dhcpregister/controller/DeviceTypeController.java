package br.dev.luisgustavosales.dhcpregister.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.dev.luisgustavosales.dhcpregister.entities.DeviceRegister;
import br.dev.luisgustavosales.dhcpregister.entities.DeviceType;
import br.dev.luisgustavosales.dhcpregister.repositories.DeviceTypeRepository;
import br.dev.luisgustavosales.dhcpregister.services.DeviceRegisterService;

@RestController
@RequestMapping("/devicestypes")
public class DeviceTypeController {

	@Autowired
	private DeviceTypeRepository deviceTypeRepository;
	
	
	@GetMapping
	public ResponseEntity<List<DeviceType>> findAll() {
		
		var devicesTypes = this.deviceTypeRepository.findAll();
		
		return ResponseEntity.ok(devicesTypes);
	}
	
}
