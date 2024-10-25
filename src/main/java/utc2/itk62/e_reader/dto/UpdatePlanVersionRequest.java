package utc2.itk62.e_reader.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Primary;

import java.time.Instant;

@Data
@Builder
public class UpdatePlanVersionRequest {
    private Long planId;
    private Long planVersionId;
    private Instant effectiveTime;
    private int maxEnrollBook;
    private double price;
}
