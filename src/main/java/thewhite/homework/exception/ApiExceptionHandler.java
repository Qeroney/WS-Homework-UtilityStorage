package thewhite.homework.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public MessageError processNotFoundException(NotFoundException exception) {
        return MessageError.of(exception.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public MessageError processConstViolationException(ConstraintViolationException exception) {
        return MessageError.of(exception.getMessage());
    }
}
