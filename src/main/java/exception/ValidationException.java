package exception;

import lombok.Data;

import java.util.List;

@Data
public class ValidationException extends Exception {
    private final List<String> validations;

    public ValidationException(List<String> validations){
        this.validations = validations;
    }
}
