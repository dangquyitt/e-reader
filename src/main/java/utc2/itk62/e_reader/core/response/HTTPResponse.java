package utc2.itk62.e_reader.core.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HTTPResponse<T> {
    private String message;
    private Object data;

    public static  <T> HTTPResponse<T> ok(T body) {
        return new HTTPResponse<T>("success", body);
    }
}


