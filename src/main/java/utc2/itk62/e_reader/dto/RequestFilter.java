package utc2.itk62.e_reader.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.model.OrderBy;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestFilter<T> {
    private T filter;
    private OrderBy orderBy;
    private Pagination pagination;
}
