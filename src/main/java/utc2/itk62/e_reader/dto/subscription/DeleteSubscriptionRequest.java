package utc2.itk62.e_reader.dto.subscription;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteSubscriptionRequest {
    @NotNull
    private Long id;
}
