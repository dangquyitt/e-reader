package utc2.itk62.e_reader.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class SubscriptionResponse {

    private Long id;
    private Long userId;
    private Long planId;
    private Instant startTime;
    private Instant endTime;

}
