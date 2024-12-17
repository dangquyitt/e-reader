package utc2.itk62.e_reader.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "role_permissions", schema = "public", indexes = {
        @Index(name = "role_permissions_role_id_permission_id_idx", columnList = "role_id, permission_id", unique = true)
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "id", nullable = false)),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false))
})
public class RolePermission extends BaseEntity {
    @NotNull
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @NotNull
    @Column(name = "permission_id", nullable = false)
    private Long permissionId;

}