package org.hta.domain.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@ToString
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "permission", schema = "hta")
public class Permission extends EntityBase {

    @Id
    @Column(name = "permission_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int permissionId;

    @Column(name = "permission_name")
    private String permissionName;

    @Column(name = "permission_description")
    private String permissionDescription;

    @Column(name = "permission_type")
    @Enumerated(EnumType.STRING)
    private PermissionType permissionType;

    @ManyToMany(mappedBy = "rolePermission")
    private List<Role> roles;
}
