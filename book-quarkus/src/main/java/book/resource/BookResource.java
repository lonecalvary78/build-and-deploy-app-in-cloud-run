package book.resource;

import book.dao.BookDao;
import book.exception.DuplicateBookException;
import book.exception.NoSuchBookException;
import book.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.util.List;

@Path("/books")
public class BookResource {
    private Logger logger = LoggerFactory.getLogger(BookResource.class);

    @Inject
    private BookDao bookDao;

    @GET
    public List<Book> retrieveAll(@QueryParam("pageNum") @DefaultValue("1") int pageNumber) {
        return bookDao.retrieveAll(pageNumber);
    }

    @GET
    @Path("/{id}")
    public Book viewExisting(@PathParam("id") Long bookId) throws NoSuchBookException {
        return bookDao.viewExisting(bookId);
    }

    @POST
    public void newEntry(@Valid Book book) throws DuplicateBookException {
        bookDao.newEntry(book);
    }

    @PUT
    @Path("/{id}")
    public void updateExisting(@PathParam("id") Long bookId, Book modifiedBook) throws NoSuchBookException {
        bookDao.updateExisting(bookId, modifiedBook);
    }

    @DELETE
    @Path("/{id}")
    public void removeExisting(@PathParam("id") Long bookId) throws NoSuchBookException {
        bookDao.removeExisting(bookId);
    }
}
