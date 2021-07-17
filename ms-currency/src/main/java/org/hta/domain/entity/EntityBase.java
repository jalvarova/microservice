package org.hta.domain.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class EntityBase {

    protected Boolean state = Boolean.TRUE;

    @CreationTimestamp
    protected LocalDateTime createdAt = LocalDateTime.now();

    @UpdateTimestamp
    protected LocalDateTime updatedAt = LocalDateTime.now();

}
