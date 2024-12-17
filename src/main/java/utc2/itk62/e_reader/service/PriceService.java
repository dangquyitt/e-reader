package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.dto.price.CreatePriceRequest;

public interface PriceService {
    Long createPrice(CreatePriceRequest createPriceRequest);
}
