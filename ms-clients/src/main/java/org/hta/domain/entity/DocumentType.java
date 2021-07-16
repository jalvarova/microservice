package org.hta.domain.entity;


import lombok.*;

import javax.persistence.*;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "document_type", schema = "hta")
@Builder
public class DocumentType extends EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_type_id")
    private Long documentTypeId;

    @Column(unique = true, length = 2)
    private String type;

    @Column(unique = true, length = 60)
    private String description;

    @Column(unique = true, length = 20)
    private String shortDescription;
}
