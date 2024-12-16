package utc2.itk62.e_reader.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book_tags", schema = "public", indexes = {
        @Index(name = "book_tags_book_id_tag_id_idx", columnList = "book_id, tag_id", unique = true)
})
public class BookTag extends BaseEntity {
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "tag_id")
    private Long tagId;

}