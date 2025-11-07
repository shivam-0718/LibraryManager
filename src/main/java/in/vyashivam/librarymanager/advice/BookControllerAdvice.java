package in.vyashivam.librarymanager.advice;

import in.vyashivam.librarymanager.exception.BookAlreadyReturnedException;
import in.vyashivam.librarymanager.exception.BookIsUnavailableException;
import in.vyashivam.librarymanager.exception.BookNotFoundException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
public class BookControllerAdvice {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleBookNotFoundException(BookNotFoundException bnf) {
        ErrorDetails error = new ErrorDetails(bnf.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookIsUnavailableException.class)
    public ResponseEntity<ErrorDetails> handleBookIsUnavailableException(BookIsUnavailableException buna) {
        ErrorDetails error = new ErrorDetails(buna.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookAlreadyReturnedException.class)
    public ResponseEntity<ErrorDetails> handleBookAlreadyReturnedException(BookAlreadyReturnedException bar) {
        ErrorDetails error = new ErrorDetails(bar.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    //Handling invalid enum values in request parameters or path values
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDetails> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message;

        //Checking if the mismatched type is an enum
        if(ex.getRequiredType() != null && ex.getRequiredType().isEnum()) {
            // Extract all valid enum constants and format them as a comma-separated string
            Object[] enumConstants = ex.getRequiredType().getEnumConstants();
            String validValues = Arrays.stream(enumConstants).
                    map(Object::toString).
                    collect(Collectors.joining(", "));

            // Provide detailed message with valid enum values
            message = String.format("Invalid value '%s' for parameter '%s'. Valid values are: %s", ex.getValue(), ex.getName(), validValues);
        } else {
            message = String.format("Invalid value '%s' for parameter '%s'", ex.getValue(), ex.getName());
        }

        ErrorDetails error = new ErrorDetails(message, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleOtherException(Exception e) {
        ErrorDetails error = new ErrorDetails(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
