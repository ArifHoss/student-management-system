package se.iths.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionHandler extends WebApplicationException {

    public NotFoundExceptionHandler(String message) {
         super(Response.status(Response.Status.NOT_FOUND).entity(message).type(MediaType.TEXT_PLAIN_TYPE).build());

    }
}
