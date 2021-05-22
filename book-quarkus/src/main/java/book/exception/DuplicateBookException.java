package book.exception;

public class DuplicateBookException extends Exception {
    public DuplicateBookException(String isbn) {
        super(String.format("Unable to process this new entry due to duplicate entry violation [ISBN: %s].",isbn));
    }
}
