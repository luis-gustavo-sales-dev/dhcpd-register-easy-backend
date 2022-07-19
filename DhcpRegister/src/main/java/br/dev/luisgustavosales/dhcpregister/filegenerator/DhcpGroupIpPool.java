package br.dev.luisgustavosales.dhcpregister.filegenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import lombok.Data;

@Data
public class DhcpGroupIpPool {
	private Long groupDeviceId;
	// Os Ips ficarão na Stack<String>
	private List<Stack<String>> range = new ArrayList<>();
	
	
	public DhcpGroupIpPool(Long groupDeviceId, Integer quantityRanges) {
		// Inicializa as stages de ranges
		setGroupDeviceId(groupDeviceId);
		for(int i=0; i < quantityRanges; i++) {
			range.add(new Stack<String>());
		}
	}
	
	
	
	
}
