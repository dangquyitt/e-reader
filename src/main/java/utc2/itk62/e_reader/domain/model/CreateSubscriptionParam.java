package utc2.itk62.e_reader.domain.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@Data
@Builder
public class CreateSubscriptionParam {
    private Long userId;
    private Long priceId;
    private Instant startTime;
    private Instant endTime;
    
}
