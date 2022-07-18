package br.dev.luisgustavosales.dhcpregister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import br.dev.luisgustavosales.dhcpregister.filegenerator.DhcpFileGenerator;

@SpringBootApplication
// @EnableEurekaClient
public class DhcpRegisterApplication implements CommandLineRunner{
	
	@Autowired
	private DhcpFileGenerator dhcpFileGenerator;

	public static void main(String[] args) {
		SpringApplication.run(DhcpRegisterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		dhcpFileGenerator.generateFileContent();
	}

}
