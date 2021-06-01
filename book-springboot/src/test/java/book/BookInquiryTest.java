package book;

import book.model.Book;
import book.repo.BookRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class BookInquiryTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    public void dataSetup() {
        var book = new Book();
        book.setIsbn("1234");
        book.setTitle("Title 1234");
        book.setAuthor("Author 1234");
        book.setYear(2020);
        bookRepository.save(book);
    }

    @Test
    public void bookQuery() throws Exception {
        mockMvc.perform(get("/books").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @AfterAll
    public void cleanUp() {
        bookRepository.deleteAll();
    }
}
