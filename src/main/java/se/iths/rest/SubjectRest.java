package se.iths.rest;


import se.iths.entity.Subject;
import se.iths.exception.NotFoundExceptionHandler;
import se.iths.service.services.SubjectService;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/subject")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubjectRest {

    private final SubjectService subjectService;

    @Inject
    public SubjectRest(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GET
    @Path("")
    public Response getAllSubject(){
        List<Subject> subjects = subjectService.getAllSubject();
        return Response.ok(subjects).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id")Long id){
        Subject subject = subjectService.getSubjectById(id);
        if (subject== null){
            throw new NotFoundExceptionHandler("SUBJECT WITH ID '"+id+"' DOES NOT FOUND!PLEASE TRY WITH A VALID ID");
        }
        return Response.ok(subject).build();
    }
    @POST
    @Path("")
    public Response create(Subject subject){
        subjectService.createSubject(subject);
        return Response.ok(subject).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id")Long id){
        subjectService.delete(id);
        return Response.ok("SUBJECT HAS BEEN DELETED").build();
    }
}
