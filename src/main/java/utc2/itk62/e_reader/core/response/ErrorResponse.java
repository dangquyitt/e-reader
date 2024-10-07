package utc2.itk62.e_reader.core.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import utc2.itk62.e_reader.core.error.Error;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ErrorResponse {
    private List<Error> errors;

    public static ResponseEntity<ErrorResponse> badRequest(List<Error> errors) {
        return ResponseEntity.badRequest().body(new ErrorResponse(errors));
    }

    public static ResponseEntity<ErrorResponse> internalServerError() {
        return ResponseEntity.internalServerError().build();
    }
}
