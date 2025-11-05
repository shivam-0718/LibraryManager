package in.vyashivam.librarymanager.advice;

import in.vyashivam.librarymanager.exception.BookAlreadyReturnedException;
import in.vyashivam.librarymanager.exception.BookIsUnavailableException;
import in.vyashivam.librarymanager.exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleOtherException(Exception e) {
        ErrorDetails error = new ErrorDetails(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
