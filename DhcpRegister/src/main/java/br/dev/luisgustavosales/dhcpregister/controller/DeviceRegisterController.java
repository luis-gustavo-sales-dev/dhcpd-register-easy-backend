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

import br.dev.luisgustavosales.dhcpregister.dtos.BulkCreateDeviceRegisterDTO;
import br.dev.luisgustavosales.dhcpregister.entities.DeviceRegister;
import br.dev.luisgustavosales.dhcpregister.services.DeviceRegisterService;

@RestController
@RequestMapping("/deviceregisters")
public class DeviceRegisterController {

	@Autowired
	private DeviceRegisterService deviceRegisterService;
	
	@GetMapping("/{cpf}/{mac}")
	public ResponseEntity<DeviceRegister> findByCpfAndMac(
			@PathVariable String cpf,
			@PathVariable String mac) {
		
		System.out.println("cpf: " + cpf);
		System.out.println("mac: " + mac);
		
		var deviceRegister = this.deviceRegisterService.findByCpfAndMac(cpf, mac);
		
		return ResponseEntity.ok(deviceRegister);
	}
	
	@GetMapping
	public ResponseEntity<List<DeviceRegister>> findByCpf(
			@RequestParam String cpf) {
		
		System.out.println("cpf: " + cpf);
		
		var deviceRegister = this.deviceRegisterService.findByCpf(cpf);
		
		if (deviceRegister == null) {
			// Retorne uma exceção
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(deviceRegister);
	}
	
	@PostMapping
	public ResponseEntity<DeviceRegister> create(
			@RequestBody DeviceRegister deviceRegister){
		
		
		
		var deviceRegisterCreated = this.deviceRegisterService.create(deviceRegister);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(deviceRegisterCreated);
		
	}
	
	@PostMapping("/bulk")
	public ResponseEntity<List<DeviceRegister>> createBulk(
			@RequestBody BulkCreateDeviceRegisterDTO bulkCreateDeviceRegisterDTO){
		
		
		
		var deviceRegisterCreated = this.deviceRegisterService.createBulk(bulkCreateDeviceRegisterDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(deviceRegisterCreated);
		
	}
	
	@PutMapping("/{cpf}/{mac}")
	public ResponseEntity<DeviceRegister> update(
			@PathVariable String cpf,
			@PathVariable String mac,
			@RequestBody DeviceRegister deviceRegister) {
		var dr = this.deviceRegisterService.update(cpf, mac, deviceRegister);
		return ResponseEntity.ok(dr);
		
	}
	
	@DeleteMapping("/{cpf}/{mac}")
	public ResponseEntity<Void> delete(
			@PathVariable String cpf, 
			@PathVariable String mac) {
		this.deviceRegisterService.deleteDeviceRegisterByCpfAndMac(cpf, mac);
		return ResponseEntity.noContent().build();
	}
}
