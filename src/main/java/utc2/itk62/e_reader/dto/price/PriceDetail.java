package utc2.itk62.e_reader.dto.price;

import lombok.Builder;
import lombok.Data;
import utc2.itk62.e_reader.domain.entity.Plan;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Builder
public class PriceDetail {
    private Long id;
    private BigDecimal amount;
    private String currency;
    private Plan plan;
    private List<String> features;
    private String durationUnit;
    private int duration;
    private Instant effectiveDate;
}
