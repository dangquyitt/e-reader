package utc2.itk62.e_reader.core.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Pagination {
    int page;
    int pageSize;
    int nextPage;
    int prevPage;
    int total;

    public Pagination(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }
}
