package br.dev.luisgustavosales.dhcpregister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.dev.luisgustavosales.dhcpregister.configs.StartupConfigs;

@SpringBootApplication
// @EnableEurekaClient
public class DhcpRegisterApplication implements CommandLineRunner{
	
	@Autowired
	private StartupConfigs startupConfigs;

	public static void main(String[] args) {
		SpringApplication.run(DhcpRegisterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		startupConfigs.onStartUp();
	}

}
