package utc2.itk62.e_reader.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class TagFilter {
    List<Long> ids;
    String name;
    Long bookIdNe;
}
