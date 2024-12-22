package utc2.itk62.e_reader.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.dto.price.CreatePriceRequest;
import utc2.itk62.e_reader.service.PriceService;

@RestController
@RequestMapping("/api/prices")
@AllArgsConstructor
public class PriceController {
    private final PriceService priceService;

    @PostMapping
    public ResponseEntity<HTTPResponse> createPrice(@RequestBody CreatePriceRequest createPriceRequest) {
        return HTTPResponse.success("", priceService.createPrice(createPriceRequest));
    }
}
