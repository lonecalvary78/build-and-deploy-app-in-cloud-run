package book.exception;

public class NoSuchBookException extends Exception {
    public NoSuchBookException(Long bookId) {
        super(String.format("Unable to find the book for this reference since it was not exist [Book ID: %d].",bookId));
    }
}