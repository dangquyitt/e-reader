package utc2.itk62.e_reader.domain.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "books")
public class Book extends BaseEntity{

    private String title;
    private String author;
    private int publishedYear;
    private int totalPage;
    private String genre;
    private String language;
    private String fileUrl;

}
