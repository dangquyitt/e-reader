package utc2.itk62.e_reader.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import utc2.itk62.e_reader.core.pagination.Pagination;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HTTPResponse {
    private String message;
    private String code;
    private Object data;
    private Pagination pagination;

    public static ResponseEntity<HTTPResponse> noContent() {
        return ResponseEntity.noContent().build();
    }

    public static ResponseEntity<HTTPResponse> success(String message, Object data, Pagination pagination) {
        return ResponseEntity.ok().body(
                HTTPResponse.builder()
                        .message(message)
                        .pagination(pagination)
                        .data(data)
                        .build()
        );
    }

    public static ResponseEntity<HTTPResponse> success(String message, Object data) {
        return ResponseEntity.ok().body(
                HTTPResponse.builder()
                        .message(message)
                        .data(data)
                        .build()
        );
    }

    public static ResponseEntity<HTTPResponse> success(String message) {
        return ResponseEntity.ok().body(
                HTTPResponse.builder()
                        .message(message)
                        .build()
        );
    }

    public static ResponseEntity<HTTPResponse> success(Object data) {
        return ResponseEntity.ok().body(
                HTTPResponse.builder()
                        .data(data)
                        .build()
        );
    }
}
