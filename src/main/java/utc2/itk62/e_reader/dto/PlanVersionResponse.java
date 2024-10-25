package utc2.itk62.e_reader.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PlanVersionResponse {
    private Long id;
    private Instant effectiveTime;
    private int maxEnrollBook;
    private double price;
    private PlanResponse planResponse;
}
