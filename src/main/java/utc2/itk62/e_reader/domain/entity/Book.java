package utc2.itk62.e_reader.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "total_page", nullable = false)
    private int totalPage;

    private float rating;

    @Column(name = "published_year")
    private int publishedYear;

    @Column(name = "cover_image_url", nullable = false)
    private String coverImageUrl;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Favorite> favorites;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReadingProgress> readingProgresses;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rating> ratings;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookTag> bookTags;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookAuthor> bookAuthors;

    @ManyToMany
    private Set<Collection> collections;

    @ManyToMany
    private Set<Author> authors;

    @ManyToMany
    private Set<Tag> tags;

}
