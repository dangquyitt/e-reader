package utc2.itk62.e_reader.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class UpdatePlanVersionParam {
    private Long planVersionId;
    private Long planId;
    private Instant effectiveTime;
    private int maxEnrollBook;
    private double price;
}
