package br.dev.luisgustavosales.dhcpregister.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("properties.dhcp")
public class DhcpServiceProperties {
	private String configFile;

	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}
	
	
}
