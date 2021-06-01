package book.exception;

public class DuplicateBookEntryException extends Exception {
    public DuplicateBookEntryException(String isbn) {
        super(String.format("Unable to proceed the new entry due to duplicate entry violation [ISBN: %s].",isbn));
    }
}
