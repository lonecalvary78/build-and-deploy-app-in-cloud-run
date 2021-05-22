package book;

import book.dao.BookDao;
import book.exception.DuplicateBookException;
import book.model.Book;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import javax.inject.Inject;
import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;


@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookInquiryTest {
    @Inject
    private BookDao bookDao;

    @BeforeAll
    public void dataSetup() {
        IntStream.range(1,10).forEach(i->{
            var book = new Book();
            book.setIsbn("123"+i);
            book.setTitle("Title-"+i);
            book.setAuthor("Author-"+i);
            book.setYear(2020);
            try {
                bookDao.newEntry(book);
            } catch (DuplicateBookException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    @Order(1)
    public void performBookInquiry() {
        given().contentType(ContentType.JSON).when().get("/books").then().statusCode(200);
    }

    @AfterAll
    public void cleanUp() {
        bookDao.removeAll();
    }
}
