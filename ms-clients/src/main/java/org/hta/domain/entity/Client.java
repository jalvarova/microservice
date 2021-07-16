package org.hta.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "client", schema = "hta")
public class Client extends EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    @Size(min = 1, max = 50, message = "El nombre debe ser ingresada y tiene un cantidad de 50 caracteres")
    @Column(length = 50)
    private String name;

    @Column(length = 50)
    @Size(min = 1, max = 50, message = "El apellido debe ser ingresada y tiene un cantidad de 50 caracteres")
    private String lastName;

    @Column
    private LocalDate birdDate;

    @Column(unique = true)
    @Size(min = 8, max = 20, message = "El documento debe ser ingresada y tiene un cantidad de 20 caracteres")
    private String document;

    @Column(unique = true)
    private String businessName;

    @Column
    @Size(min = 1, max = 100, message = "La dirección debe ser ingresada y tiene un cantidad de 100 caracteres")
    private String address;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "document_type_id")
    private DocumentType documentType;
}
