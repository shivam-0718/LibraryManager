package in.vyashivam.librarymanager.exception;

public class BookIsUnavailableException extends RuntimeException {
    public BookIsUnavailableException(String message) {
        super(message);
    }
}
