package org.hta.domain.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "contact", schema = "hta")
@Builder
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Contact extends EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<ContactValue> contactValues;

    @Column
    private Boolean isPrimary;

    @Column
    private String document;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "contact", targetEntity = Client.class)
    private List<Client> clients;
}
