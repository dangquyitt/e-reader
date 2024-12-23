package utc2.itk62.e_reader.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookFilter {
    List<Long> ids;
    List<Long> collectionIds;
    List<Long> tagIds;
    List<Long> authorIds;
    String title;
    String q;
}
