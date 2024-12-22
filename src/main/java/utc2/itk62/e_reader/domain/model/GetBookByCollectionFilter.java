package utc2.itk62.e_reader.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBookByCollectionFilter {
    List<Long> collectionIds;

}
