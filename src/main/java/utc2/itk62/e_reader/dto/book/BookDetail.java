package utc2.itk62.e_reader.dto.book;

import lombok.Builder;
import lombok.Data;
import utc2.itk62.e_reader.domain.entity.Comment;

import java.util.List;

@Data
@Builder
public class BookDetail {
    private Long id;
    private String title;
    private String description;
    private int totalPage;
    private Double rating;
    private Integer publishedYear;
    private String coverImageUrl;
    private String fileUrl;
    List<Comment> comments;

}
