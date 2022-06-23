package br.dev.luisgustavosales.dhcpregister.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.dev.luisgustavosales.dhcpregister.entities.DeviceUserGroup;
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
	
}
