package utc2.itk62.e_reader.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class PlanFilter {
    List<Long> ids;
    List<Long> planIds;
    String name;
}
