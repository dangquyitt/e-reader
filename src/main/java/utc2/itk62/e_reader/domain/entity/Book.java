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
public class Book extends BaseEntity{

    private String title;
    private String description;
    private int totalPage;
    private float rating;
    private int publishedYear;
    private String coverImageUrl;
    private String fileUrl;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Favorite> favorites;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Comment> commentByUser;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<ReadingProgress> readingProgresses;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Rating> ratings;

    @ManyToMany
    private Set<Collection> collections;

    @ManyToMany
    private Set<Author> authors;

    @ManyToMany
    private Set<Tag> tags;

}
