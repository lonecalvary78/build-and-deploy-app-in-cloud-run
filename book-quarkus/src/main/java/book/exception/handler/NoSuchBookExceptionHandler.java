package book.exception.handler;


import book.exception.NoSuchBookException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoSuchBookExceptionHandler implements ExceptionMapper<NoSuchBookException> {
    @Override
    public Response toResponse(NoSuchBookException exception) {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
