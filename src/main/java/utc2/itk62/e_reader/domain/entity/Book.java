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
@Table(name = "books", schema = "public")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "id", nullable = false)),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false))
})
public class Book extends BaseEntity {
    @NotNull
    @ColumnDefault("''")
    @Column(name = "title", nullable = false, length = Integer.MAX_VALUE)
    private String title;

    @NotNull
    @ColumnDefault("''")
    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    private String description;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "total_page", nullable = false)
    private Integer totalPage;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "published_year")
    private Integer publishedYear;

    @NotNull
    @Column(name = "cover_image_url", nullable = false, length = Integer.MAX_VALUE)
    private String coverImageUrl;

    @NotNull
    @Column(name = "file_url", nullable = false, length = Integer.MAX_VALUE)
    private String fileUrl;

}