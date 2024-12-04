package utc2.itk62.e_reader.exception;

import lombok.Data;

@Data
public class EReaderException extends RuntimeException {
    private String code;
    private Object[] args;

    public EReaderException(String code, Object... args) {
        this.code = code;
        this.args = args;
    }
}
