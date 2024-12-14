package utc2.itk62.e_reader.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "permissions", schema = "public", indexes = {
        @Index(name = "permissions_http_method_path_idx", columnList = "http_method, path")
})
public class Permission extends BaseEntity {
    @NotNull
    @Column(name = "http_method", nullable = false, length = Integer.MAX_VALUE)
    private String httpMethod;

    @NotNull
    @Column(name = "path", nullable = false, length = Integer.MAX_VALUE)
    private String path;

    @OneToMany(mappedBy = "permission")
    private Set<RolePermission> rolePermissions = new LinkedHashSet<>();

    public Permission(String httpMethod, String path) {
        this.httpMethod = httpMethod;
        this.path = path;
    }
}