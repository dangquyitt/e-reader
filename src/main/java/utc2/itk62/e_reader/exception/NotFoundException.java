package utc2.itk62.e_reader.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends RuntimeException {
    private String field;
    private String message;
    private Object[] args;

    public NotFoundException(String field, String message, Object... args) {
        this.field = field;
        this.message = message;
        this.args = args;
    }
}
