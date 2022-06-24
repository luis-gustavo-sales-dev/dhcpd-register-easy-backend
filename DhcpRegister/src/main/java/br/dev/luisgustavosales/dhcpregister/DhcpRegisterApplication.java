package br.dev.luisgustavosales.dhcpregister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DhcpRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(DhcpRegisterApplication.class, args);
	}

}
