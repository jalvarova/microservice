package org.hta.domain.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "role", schema = "hta")
public class Role extends EntityBase implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_description")
    private String roleDescription;

    @ManyToMany(mappedBy = "userRoles")
    private List<User> users;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            schema = "hta",
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "permission_id")
    )
    private List<Permission> rolePermission = new ArrayList<>();

    @Override
    public String getAuthority() {
        return this.roleName;
    }
}
