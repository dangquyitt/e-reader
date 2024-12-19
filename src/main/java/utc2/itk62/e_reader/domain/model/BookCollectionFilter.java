package utc2.itk62.e_reader.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class BookCollectionFilter {
    List<Long> ids;
    List<Long> collectionIds;
}
