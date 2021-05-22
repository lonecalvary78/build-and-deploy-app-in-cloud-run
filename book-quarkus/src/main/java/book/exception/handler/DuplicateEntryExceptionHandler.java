package book.exception.handler;

import book.exception.DuplicateBookException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DuplicateEntryExceptionHandler implements ExceptionMapper<DuplicateBookException> {
    @Override
    public Response toResponse(DuplicateBookException exception) {
        return Response.status(Response.Status.CONFLICT).build();
    }
}
