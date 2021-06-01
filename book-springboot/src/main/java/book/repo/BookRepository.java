package book.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import book.model.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    @Query("SELECT book FROM Book book WHERE book.isbn=:isbn")
    public Optional<Book> findBookByIsbn(@Param("isbn") String isbn);
}
