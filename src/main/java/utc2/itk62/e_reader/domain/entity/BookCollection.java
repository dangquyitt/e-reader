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
@Table(name = "book_collections", schema = "public", indexes = {
        @Index(name = "book_collections_book_id_collection_id_idx", columnList = "book_id, collection_id", unique = true)
})
public class BookCollection extends BaseEntity {
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "collection_id")
    private Long collectionId;

}