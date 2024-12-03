package utc2.itk62.e_reader.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utc2.itk62.e_reader.core.pagination.Pagination;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestFilter<T> {
    private T filter;
    private Pagination pagination;
}
