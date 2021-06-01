package book.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Book")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private int year;

    public Book mergeWith(Book modifiedBook) {
        setTitle(modifiedBook.getTitle());
        setAuthor(modifiedBook.getAuthor());
        setYear(modifiedBook.getYear());
        return this;
    }
}
