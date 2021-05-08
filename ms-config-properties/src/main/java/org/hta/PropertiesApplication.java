package org.hta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class PropertiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropertiesApplication.class, args);
	}

}
