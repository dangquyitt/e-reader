package utc2.itk62.e_reader.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
