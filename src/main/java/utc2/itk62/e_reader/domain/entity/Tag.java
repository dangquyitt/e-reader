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
@Table(name = "tags", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "tags_name_key", columnNames = {"name"})
})
public class Tag extends BaseEntity {
    @NotNull
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @OneToMany(mappedBy = "tag")
    private Set<BookTag> bookTags = new LinkedHashSet<>();

}