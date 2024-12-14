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
@Table(name = "collections", schema = "public", indexes = {
        @Index(name = "collections_user_id_name_idx", columnList = "user_id, name", unique = true)
}, uniqueConstraints = {
        @UniqueConstraint(name = "collections_name_key", columnNames = {"name"})
})
public class Collection extends BaseEntity {
    @NotNull
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "collection")
    private Set<BookCollection> bookCollections = new LinkedHashSet<>();

}