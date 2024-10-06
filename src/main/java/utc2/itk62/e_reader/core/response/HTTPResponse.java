package utc2.itk62.e_reader.core.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import utc2.itk62.e_reader.core.pagination.Pagination;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HTTPResponse<T> {
    private T data;
    private Pagination pagination;
    public static <T> ResponseEntity<HTTPResponse<T>> ok(T data) {
        return ResponseEntity.status(HttpStatus.OK).body(new HTTPResponse<>(data, null));
    }
    public static <T> ResponseEntity<HTTPResponse<T>>  ok(T data, Pagination pagination) {
        return ResponseEntity.status(HttpStatus.OK).body(new HTTPResponse<>(data, pagination));
    }
}


