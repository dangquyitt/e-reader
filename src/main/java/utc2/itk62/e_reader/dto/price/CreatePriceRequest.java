package utc2.itk62.e_reader.dto.price;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class CreatePriceRequest {
    private Long planId;
    private Map<String, Object> metadata;
    private List<String> features;
    private BigDecimal amount;
    @NotEmpty
    private String currency;
    @NotNull
    private Integer duration;
    @NotNull
    private String durationUnit;
}
