package org.hta.initialize;

import lombok.extern.slf4j.Slf4j;
import org.hta.domain.entity.*;
import org.hta.domain.ClientRepository;
import org.hta.domain.DocumentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
class LoadDataBase {

    static List<Client> clients = new ArrayList<>();

    static List<Contact> contacts = new ArrayList<>();

    static {
    }

    //@Bean
    CommandLineRunner initDatabase(ClientRepository repository, DocumentRepository documentRepository) {
        return args -> {

            List<ContactValue> contactValues = Arrays.asList(
                    ContactValue.builder().type(ContactType.EMAIL).value("Alvaro92q18@gmail.com").build(),
                    ContactValue.builder().type(ContactType.TELEPHONE).value("986809252").build(),
                    ContactValue.builder().type(ContactType.LINK).value("https://www.linkedin.com/in/alvaro-daniel-aguinaga-delgado-29aa10119/").build()
            );


            DocumentType documentType = documentRepository.findByType("01");

            clients.add(Client
                    .builder()
                    .name("Alvaro")
                    .lastName("Aguinaga")
                    .documentType(documentType)
                    .document("47082903")
                    .businessName("Walavo SAC")
                    .address("PEDRO LAOS HURTADO MZ A LT 3 - SJM")
                    .contact(Contact
                            .builder()
                            .contactValues(contactValues)
                            .document("47082903")
                            .build())
                    .build());


            clients.add(Client
                    .builder()
                    .name("Gonzalo")
                    .lastName("Aguinaga")
                    .documentType(documentType)
                    .document("78985851")
                    .businessName("Ecofi SAC")
                    .address("Calle San Miguel 488 San Luis")
                    .contact(Contact
                            .builder()
                            .contactValues(contactValues)
                            .document("78985851")
                            .build())
                    .build());

            repository.saveAll(clients);

            log.info("Preloading....");
        };
    }
}
