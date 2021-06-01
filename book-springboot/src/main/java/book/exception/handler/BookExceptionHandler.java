package book.exception.handler;

import book.exception.DuplicateBookEntryException;
import book.exception.NoSuchBookException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookExceptionHandler {
    @ExceptionHandler(NoSuchBookException.class)
    public ResponseEntity handleForNoSuchBookException(NoSuchBookException noSuchBookException) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(DuplicateBookEntryException.class)
    public ResponseEntity handleForDuplicateEntryViolation(DuplicateBookEntryException duplicateBookEntryException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
