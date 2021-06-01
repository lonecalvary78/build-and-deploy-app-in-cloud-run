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
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RemoveExistingBookTest {
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
    public void removeExistingWithExistingBookId() throws Exception {
        mockMvc.perform(delete("/books/{id}",testBook.getId())).andExpect(status().isOk());
    }

    @Test
    public void removeExistingWithNonExistingBookId() throws Exception {
        mockMvc.perform(delete("/books/{id}",2L)).andExpect(status().isBadRequest());
    }
}
