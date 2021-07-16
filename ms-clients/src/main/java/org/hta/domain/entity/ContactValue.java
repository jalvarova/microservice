package org.hta.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ContactValue {

    @Enumerated(value = EnumType.STRING)
    private ContactType type;

    @Column
    private String value;
}
