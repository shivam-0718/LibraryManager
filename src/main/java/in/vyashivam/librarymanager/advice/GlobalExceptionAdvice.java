package in.vyashivam.librarymanager.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

public class GlobalExceptionAdvice {
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

    // Handles invalid enum values in JSON request body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetails> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = "Invalid Request Body.";
        Throwable cause = ex.getCause();

        //Check if the root cause is an enum conversion error
        if(cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause; //cause is of Throwable type, hence downcasting to ife.

            //If target type is enum, list all valid values
            if(ife.getTargetType() != null && ife.getTargetType().isEnum()) {
                Object[] enumConstants = ife.getTargetType().getEnumConstants();
                String validValues = Arrays.stream(enumConstants)
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));

                // Provide detailed message with valid enum values
                message = String.format("Invalid value '%s' for parameter '%s'. Valid values are: %s", ife.getValue(), ife.getPath().get(0).getFieldName(), validValues);
            }

        } else {
            message = ex.getMostSpecificCause().getMessage();
        }

        ErrorDetails error = new ErrorDetails(message, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    //Handle validation error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorDetails error = new ErrorDetails(message, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    //Handling different exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleOtherException(Exception e) {
        ErrorDetails error = new ErrorDetails(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
