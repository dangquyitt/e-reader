package utc2.itk62.e_reader.dto.subscription;

import lombok.Builder;
import lombok.Data;
import utc2.itk62.e_reader.dto.price.PriceDetail;

import java.time.Instant;

@Data
@Builder
public class SubscriptionDetail {
    private Long id;
    private PriceDetail priceDetail;
    private Instant startTime;
    private Instant endTime;
}
