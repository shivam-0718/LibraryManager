package in.vyashivam.librarymanager.advice;

import in.vyashivam.librarymanager.exception.InvalidMembershipEndDateException;
import in.vyashivam.librarymanager.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class MemberControllerAdvice {
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleMemberNotFoundException(MemberNotFoundException mnf) {
        ErrorDetails error = new ErrorDetails(mnf.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidMembershipEndDateException.class)
    public ResponseEntity<ErrorDetails> handleInvalidMembershipEndDateException(InvalidMembershipEndDateException imede) {
        ErrorDetails error = new ErrorDetails(imede.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
