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
@Table(name = "permissions", schema = "public", indexes = {
        @Index(name = "permissions_http_method_path_idx", columnList = "http_method, path", unique = true)
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "id", nullable = false)),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false))
})
public class Permission extends BaseEntity {
    @NotNull
    @Column(name = "http_method", nullable = false, length = Integer.MAX_VALUE)
    private String httpMethod;

    @NotNull
    @Column(name = "path", nullable = false, length = Integer.MAX_VALUE)
    private String path;

}