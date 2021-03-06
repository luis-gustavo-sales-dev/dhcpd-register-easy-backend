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

import br.dev.luisgustavosales.dhcpregister.entities.DeviceUserGroup;
import br.dev.luisgustavosales.dhcpregister.entities.IpRangeGroup;
import br.dev.luisgustavosales.dhcpregister.services.DeviceUserGroupService;

@RestController
@RequestMapping("/deviceusergroup")
public class DeviceUserGroupController {
	
	@Autowired
	private DeviceUserGroupService deviceUserGroupService;
	
	@GetMapping("/{id}")
	public ResponseEntity<DeviceUserGroup> findById(
			@PathVariable Long id) {
		var deviceUserGroup = this.deviceUserGroupService.findById(id);
		return ResponseEntity.ok(deviceUserGroup);
	}
	
	@GetMapping
	public ResponseEntity<List<DeviceUserGroup>> findByName(
			@RequestParam String name) {
		var deviceUserGroup = this.deviceUserGroupService.findByName(name);
		return ResponseEntity.ok(deviceUserGroup);
	}
	
	@PostMapping
	public ResponseEntity<DeviceUserGroup> create(
			@RequestBody DeviceUserGroup deviceUserGroup){
		
		System.out.println("deviceUserGroup: " + deviceUserGroup);
		
		var deviceUserGroupCreated = this.deviceUserGroupService.create(deviceUserGroup);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(deviceUserGroupCreated);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<DeviceUserGroup> update(
			@PathVariable Long id,
			@RequestBody DeviceUserGroup deviceUserGroupToUpdate){
		
		var dg = this.deviceUserGroupService.findById(id);

		var dgu = this.deviceUserGroupService.update(deviceUserGroupToUpdate, dg);
		
		return ResponseEntity.ok(dgu);

		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(
			@PathVariable Long id) {
		this.deviceUserGroupService.delete(id);
		return ResponseEntity.noContent().build();
		
	}
	
}
