package utc2.itk62.e_reader.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utc2.itk62.e_reader.domain.entity.Book;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {

    private Long id;
    private String title;
    private String description;
    private int totalPage;
    private float rating;
    private int publishedYear;
    private String coverImageUrl;
    private String fileUrl;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.description = book.getDescription();
        this.totalPage = book.getTotalPage();
        this.rating = book.getRating();
        this.publishedYear = book.getPublishedYear();
        this.coverImageUrl = book.getCoverImageUrl();
        this.fileUrl = book.getFileUrl();
    }
}
