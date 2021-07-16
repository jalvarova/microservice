package org.hta;

import org.hta.domain.ClientRepository;
import org.hta.domain.ContactRepository;
import org.hta.domain.DocumentRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableDiscoveryClient
@EnableJpaRepositories(basePackageClasses = {ClientRepository.class, ContactRepository.class, DocumentRepository.class})
@SpringBootApplication
public class MsClientsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsClientsApplication.class, args);
    }

}
