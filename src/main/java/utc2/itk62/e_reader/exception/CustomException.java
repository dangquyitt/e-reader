package utc2.itk62.e_reader.exception;

import lombok.*;
import utc2.itk62.e_reader.core.error.Error;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CustomException extends RuntimeException{
    private int status;
    // TODO: hide this field when response to client. Just log ex to debug
    private Exception exception;
    private List<Error> errors;

    public CustomException addError(Error error){
        if(this.errors == null){
            this.errors = new ArrayList<>();
        }
        this.errors.add(error);
        return this;
    }

    public CustomException setStatus(int status){
        this.status = status;
        return this;
    }

    public CustomException setException(Exception exception){
        this.exception = exception;
        return this;
    }
}
