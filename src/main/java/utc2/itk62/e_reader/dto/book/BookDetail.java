package utc2.itk62.e_reader.dto.book;

import lombok.Builder;
import lombok.Data;
import utc2.itk62.e_reader.domain.entity.Collection;
import utc2.itk62.e_reader.domain.entity.Comment;
import utc2.itk62.e_reader.domain.entity.Tag;

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
    private List<Comment> comments;
    private boolean isFavorite;
    private List<Collection> collections;
    private List<Tag> tags;
}
