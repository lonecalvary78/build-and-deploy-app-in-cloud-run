package book.dao;

import book.exception.DuplicateBookException;
import book.exception.NoSuchBookException;
import book.model.Book;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookDao {
    @Inject
    private EntityManager entityManager;

    @ConfigProperty(name="maximum-record-per-page", defaultValue = "10")
    private int maximumRecordPerPage;

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Book> retrieveAll(int pageNumber) {
        return entityManager.createQuery("SELECT book FROM Book book").setFirstResult((pageNumber-1)*maximumRecordPerPage).setMaxResults(maximumRecordPerPage).getResultList();
    }

    @Transactional
    public Book viewExisting(Long bookId) throws NoSuchBookException {
        return Optional.ofNullable((Book)entityManager.find(Book.class,bookId)).orElseThrow(()-> new NoSuchBookException(bookId));
    }

    @Transactional
    public void newEntry(Book book) throws DuplicateBookException {
        if(anyBookUseThisReference(book.getIsbn()))
            throw new DuplicateBookException(book.getIsbn());
        else
            entityManager.persist(book);
    }

    @Transactional
    public void updateExisting(Long bookId, Book modifiedBook) throws NoSuchBookException {
        var mergedBook = Optional.ofNullable((Book)entityManager.find(Book.class,bookId)).map(existingBook->existingBook.mergeWith(modifiedBook)).orElseThrow(()-> new NoSuchBookException(bookId));
    }

    @Transactional
    public void removeExisting(Long bookId) throws NoSuchBookException {
        var book = Optional.ofNullable((Book)entityManager.find(Book.class,bookId)).orElseThrow(()-> new NoSuchBookException(bookId));
        entityManager.remove(book);
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public void removeAll() {
        var books = entityManager.createQuery("SELECT book FROM Book book").getResultList();
        books.stream().forEach(book->{
            entityManager.remove(book);
        });
        entityManager.flush();
    }

    private boolean anyBookUseThisReference(String isbn) {
        return ((long)entityManager.createQuery("select count(book) FROM Book book WHERE book.isbn=:isbn")
                .setParameter("isbn",isbn)
                .getSingleResult())>0;
    }
}