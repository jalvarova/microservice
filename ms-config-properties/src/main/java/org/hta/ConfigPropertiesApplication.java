package org.hta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigPropertiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigPropertiesApplication.class, args);
	}

}
