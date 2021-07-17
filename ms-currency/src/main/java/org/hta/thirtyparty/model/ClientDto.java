package org.hta.thirtyparty.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hta.thirtyparty.model.ContactDto;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name", "lastName", "documentId", "document", "documentType", "businessName", "address", "contacts"})
public class ClientDto {

    private Long id;

    private String name;

    private String lastName;

    private String documentId;

    private String documentType;

    private String document;

    private String businessName;

    private String address;

    private List<ContactDto> contacts;
}