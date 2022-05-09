package se.iths.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ConflictExceptionHandler extends WebApplicationException {

    public ConflictExceptionHandler(String message){
        super(Response.status(Response.Status.CONFLICT)
                .entity(new ErrorMessage(message)).type(MediaType.TEXT_PLAIN_TYPE).build());
    }
}
