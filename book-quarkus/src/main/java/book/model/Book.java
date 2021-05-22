package book.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "ISBN should not be empty")
    private String isbn;

    @NotBlank(message = "Book title should not be empty")
    private String title;

    @NotBlank(message = "Book author should not be empty")
    private String author;

    @Min(value = 1949,message = "Book publish year should be at lease 1950 or later")
    private int year;
    private LocalDate createdAt;
    private LocalDate lastModifiedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(LocalDate lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public Book mergeWith(Book modifiedBook) {
        setTitle(modifiedBook.getTitle());
        setAuthor(modifiedBook.getAuthor());
        setYear(modifiedBook.getYear());
        return this;
    }

    @PrePersist
    public void beforeSaveNew() {
        setCreatedAt(LocalDate.now());
    }

    @PreUpdate
    public void beforeUpdate() {
        setLastModifiedAt(LocalDate.now());
    }
}