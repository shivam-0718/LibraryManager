package in.vyashivam.librarymanager.advice;

import in.vyashivam.librarymanager.exception.LoanNotFoundException;
import in.vyashivam.librarymanager.exception.MaxActiveLoansExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class LoanControllerAdvice {
    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleLoanNotFoundException(LoanNotFoundException lnf) {
        ErrorDetails error = new ErrorDetails(lnf.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxActiveLoansExceededException.class)
    public ResponseEntity<ErrorDetails> handleMaxActiveLoansExceededException(MaxActiveLoansExceededException ex) {
        ErrorDetails error = new ErrorDetails(ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
