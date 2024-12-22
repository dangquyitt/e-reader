package utc2.itk62.e_reader.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionFilter {
    List<Long> ids;
    Long userId;
    Long priceId;
    
}
