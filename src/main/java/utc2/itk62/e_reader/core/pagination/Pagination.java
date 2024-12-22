package utc2.itk62.e_reader.core.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Pagination {
    private int page;
    private int pageSize;
    private int nextPage;
    private int prevPage;
    private long total;
}
