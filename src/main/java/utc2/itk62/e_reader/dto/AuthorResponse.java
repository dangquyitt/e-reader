package utc2.itk62.e_reader.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorResponse {

    private Long id;
    private String authorName;

}
