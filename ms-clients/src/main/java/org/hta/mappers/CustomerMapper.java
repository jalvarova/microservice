package org.hta.mappers;

import org.hta.domain.entity.Client;
import org.hta.domain.entity.Contact;
import org.hta.domain.entity.ContactValue;
import org.hta.dto.ClientDTO;
import org.hta.dto.ContactDTO;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@FunctionalInterface
public interface CustomerMapper {

    void example();

    Function<Client, ClientDTO> entityToApi = (Client entity) ->
            ClientDTO
                    .builder()
                    .id(entity.getClientId())
                    .name(entity.getName())
                    .lastName(entity.getLastName())
                    .documentId(entity.getDocumentType().getType())
                    .documentType(entity.getDocumentType().getShortDescription())
                    .document(entity.getDocument())
                    .businessName(entity.getBusinessName())
                    .address(entity.getAddress())
                    .contacts(entity
                            .getContact()
                            .getContactValues()
                            .stream()
                            .map(contact -> ContactDTO
                                    .builder()
                                    .type(contact.getType())
                                    .value(contact.getValue())
                                    .build())
                            .collect(Collectors.toList()))
                    .build();

    Function<ClientDTO, Client> apiToEntity = (ClientDTO api) -> {

        List<ContactValue> contactValues = api
                .getContacts()
                .stream()
                .map(contactDTO -> ContactValue
                        .builder()
                        .value(contactDTO.getValue())
                        .type(contactDTO.getType())
                        .build())
                .collect(Collectors.toList());

        return Client
                .builder()
                .clientId(api.getId())
                .name(api.getName())
                .lastName(api.getLastName())
                .document(api.getDocument())
                .businessName(api.getBusinessName())
                .address(api.getAddress())
                .contact(Contact
                        .builder()
                        .document(api.getDocument())
                        .contactValues(contactValues)
                        .build())
                .build();
    };

}
