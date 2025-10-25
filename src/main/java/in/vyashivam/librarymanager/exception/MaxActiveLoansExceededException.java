package in.vyashivam.librarymanager.exception;

public class MaxActiveLoansExceededException extends RuntimeException {
    public MaxActiveLoansExceededException(String message) {
        super(message);
    }
}
