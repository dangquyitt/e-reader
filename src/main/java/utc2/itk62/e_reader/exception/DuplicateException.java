package utc2.itk62.e_reader.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateException extends RuntimeException {
    public DuplicateException(String message) {
        super(message);
    }
}
