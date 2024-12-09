package utc2.itk62.e_reader.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "book_tags")
public class BookTag extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;


}
