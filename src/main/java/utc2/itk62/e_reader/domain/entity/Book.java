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
@Table(name = "books", schema = "public")
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
    @Column(name = "\"totalPage\"", nullable = false)
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

    @OneToMany(mappedBy = "book")
    private Set<BookAuthor> bookAuthors = new LinkedHashSet<>();

    @OneToMany(mappedBy = "book")
    private Set<BookCollection> bookCollections = new LinkedHashSet<>();

    @OneToMany(mappedBy = "book")
    private Set<BookTag> bookTags = new LinkedHashSet<>();

    @OneToMany(mappedBy = "book")
    private Set<Comment> comments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "book")
    private Set<Favorite> favorites = new LinkedHashSet<>();

    @OneToMany(mappedBy = "book")
    private Set<Rating> ratings = new LinkedHashSet<>();

    @OneToMany(mappedBy = "book")
    private Set<ReadingProgress> readingProgresses = new LinkedHashSet<>();

}