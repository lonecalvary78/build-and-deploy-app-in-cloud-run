package book;

import book.model.Book;
import book.repo.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UpdateExistingBookTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    private Book testBook;

    @BeforeAll
    public void dataSetup() {
        testBook = new Book();
        testBook.setIsbn("1234");
        testBook.setTitle("Title 1234");
        testBook.setAuthor("Author 1234");
        testBook.setYear(2020);
        testBook = bookRepository.save(testBook);
    }

    @Test
    @Order(1)
    public void updateExistingWithExistingBookId() throws Exception {
       testBook.setTitle("Title 234");
       mockMvc.perform(put("/books/{id}",testBook.getId()).contentType(MediaType.APPLICATION_JSON).content(convert(testBook))).andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void updateExistingWithNonExistingBookId() throws Exception{
        testBook.setTitle("Title 234");
        mockMvc.perform(put("/books/{id}",2L).contentType(MediaType.APPLICATION_JSON).content(convert(testBook))).andExpect(status().isBadRequest());
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
