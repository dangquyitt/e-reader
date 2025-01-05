package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.Price;
import utc2.itk62.e_reader.domain.model.OrderBy;
import utc2.itk62.e_reader.domain.model.PriceFilter;
import utc2.itk62.e_reader.dto.price.CreatePriceRequest;

import java.util.List;

public interface PriceService {
    Long createPrice(CreatePriceRequest createPriceRequest);

    List<Price> getListPrice(PriceFilter filter, OrderBy orderBy, Pagination pagination);
}
