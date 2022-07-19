package br.dev.luisgustavosales.dhcpregister.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.luisgustavosales.dhcpregister.filegenerator.DhcpFileGenerator;

@Service
public class StartupConfigs {
	
	@Autowired
	private DhcpFileGenerator dhcpFileGenerator;
	
	public void onStartUp() {
		dhcpFileGenerator.generateFile();
	}
}
