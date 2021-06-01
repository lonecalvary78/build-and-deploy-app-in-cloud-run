package book.controller;

import book.exception.DuplicateBookEntryException;
import book.exception.NoSuchBookException;
import book.model.Book;
import book.repo.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookRepository bookRepository;

    @Value("${pagination.maximum-record-per-page}")
    private int maximumRecordPerPage;

    @GetMapping
    public List<Book> bookInquiry(@RequestParam(value = "pageNumber",required = false, defaultValue = "1") int pageNumber) {
        logger.info("bookInquiry");
        return bookRepository.findAll(PageRequest.of(pageNumber-1,maximumRecordPerPage)).getContent();
    }

    @GetMapping("/{id}")
    public Book viewExisting(@PathVariable("id") Long bookId) throws NoSuchBookException {
        logger.info(String.format("viewExisting[bookId: %d]",bookId));
        return bookRepository.findById(bookId).orElseThrow(()-> new NoSuchBookException(bookId));
    }

    @PostMapping
    public void newBookEntry(@RequestBody @Valid Book book) throws DuplicateBookEntryException {
        logger.info("newBookEntry");
        if(anyBookUseThisReference(book.getIsbn()))
            throw new DuplicateBookEntryException(book.getIsbn());
        bookRepository.save(book);
    }

    @PutMapping("/{id}")
    public Book updateExisting(@PathVariable("id") Long bookId, @RequestBody @Valid Book modifiedBook) throws NoSuchBookException {
        logger.info(String.format("updateExisting[bookId: %d]",bookId));
        var mergedBook = bookRepository.findById(bookId).map(existingBook-> existingBook.mergeWith(modifiedBook)).orElseThrow(()-> new NoSuchBookException(bookId));
        return bookRepository.save(mergedBook);
    }

    @DeleteMapping("/{id}")
    public void deleteExisting(@PathVariable("id") Long bookId) throws NoSuchBookException {
        logger.info(String.format("deleteExisting[bookId: %d]",bookId));
        var book = bookRepository.findById(bookId).orElseThrow(()-> new NoSuchBookException(bookId));
        bookRepository.delete(book);
    }

    private boolean anyBookUseThisReference(String isbn) {
       return bookRepository.findBookByIsbn(isbn).isPresent();
    }

}
