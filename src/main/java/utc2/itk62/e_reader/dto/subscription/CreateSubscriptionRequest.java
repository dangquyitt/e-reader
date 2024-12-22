package utc2.itk62.e_reader.dto.subscription;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
public class CreateSubscriptionRequest {
    private Long userId;
    @NotNull
    private Long priceId;
    private Instant startTime;
    private Instant endTime;
}
