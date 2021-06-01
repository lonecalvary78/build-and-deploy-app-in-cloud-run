package book;

import book.model.Book;
import book.repo.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class NewEntryBookTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void newEntryWithSuccess() throws Exception {
        var book = new Book();
        book.setIsbn("1234");
        book.setTitle("Title 1234");
        book.setAuthor("Author 1234");
        book.setYear(2020);
        mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(convert(book))).andExpect(status().isOk());
    }

    @Test
    public void newEntryWithFailed() throws Exception {
        var book = new Book();
        book.setIsbn("1234");
        book.setTitle("Title 1234");
        book.setAuthor("Author 1234");
        book.setYear(2020);
        mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(convert(book))).andExpect(status().isConflict());
    }


    @AfterAll
    public void cleanUp() {
        bookRepository.deleteAll();
    }

    private byte[] convert(Book book) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(book);
    }
}
