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
@Table(name = "book_authors", schema = "public", indexes = {
        @Index(name = "book_authors_book_id_author_id_idx", columnList = "book_id, author_id", unique = true)
})
public class BookAuthor extends BaseEntity {
    @NotNull
    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @NotNull
    @Column(name = "author_id", nullable = false)
    private Long authorId;

}