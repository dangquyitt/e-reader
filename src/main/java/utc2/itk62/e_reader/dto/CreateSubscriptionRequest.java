package utc2.itk62.e_reader.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class CreateSubscriptionRequest {
    private Long planId;
    private Long userId;

}
