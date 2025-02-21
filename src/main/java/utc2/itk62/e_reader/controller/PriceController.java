package utc2.itk62.e_reader.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.Price;
import utc2.itk62.e_reader.domain.model.PriceFilter;
import utc2.itk62.e_reader.dto.RequestFilter;
import utc2.itk62.e_reader.dto.price.CreatePriceRequest;
import utc2.itk62.e_reader.service.PriceService;

import java.util.List;

@RestController
@RequestMapping("/api/prices")
@AllArgsConstructor
public class PriceController {
    private final PriceService priceService;

    @PostMapping
    public ResponseEntity<HTTPResponse> createPrice(@RequestBody CreatePriceRequest createPriceRequest) {
        return HTTPResponse.success("", priceService.createPrice(createPriceRequest));
    }

    @PostMapping("/filter")
    public ResponseEntity<HTTPResponse> getAllPrices(@RequestBody RequestFilter<PriceFilter> filter) {
        List<Price> prices = priceService.getListPrice(filter.getFilter(), filter.getOrderBy(), filter.getPagination());
        return HTTPResponse.success("success", prices, filter.getPagination());
    }
}
