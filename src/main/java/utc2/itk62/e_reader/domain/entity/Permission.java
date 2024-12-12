package utc2.itk62.e_reader.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import utc2.itk62.e_reader.domain.enums.HTTPMethod;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "permissions")
public class Permission extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "http_method", nullable = false)
    private HTTPMethod httpMethod;

    @Column(nullable = false)
    private String path;

    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RolePermission> rolePermissions;

}
