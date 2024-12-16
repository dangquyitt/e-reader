package utc2.itk62.e_reader.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ratings", schema = "public", indexes = {
        @Index(name = "ratings_user_id_book_id_idx", columnList = "user_id, book_id", unique = true)
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "id", nullable = false)),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false))
})
public class Rating extends BaseEntity {
    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "rating", nullable = false)
    private Double rating;

}