package utc2.itk62.e_reader.domain.model;

import lombok.Data;
import utc2.itk62.e_reader.domain.entity.Price;

@Data
public class PlanInfo {
    private Long id;
    private String name;
    private Price price;
}
