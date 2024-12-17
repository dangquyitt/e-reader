package utc2.itk62.e_reader.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "favorites", schema = "public", indexes = {
        @Index(name = "favorites_user_id_book_id_idx", columnList = "user_id, book_id", unique = true)
})
public class Favorite extends BaseEntity {
    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "book_id", nullable = false)
    private Long bookId;

}