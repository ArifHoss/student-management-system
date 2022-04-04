package se.iths.rest;


import se.iths.entity.Teacher;
import se.iths.exception.NotFoundExceptionHandler;
import se.iths.service.services.TeacherService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/teacher")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TeacherRest {

    private final TeacherService teacherService;

    @Inject
    public TeacherRest(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GET
    @Path("")
    public Response getAll(){
        List<Teacher> teachers = teacherService.getAllTeacher();
        return Response.ok(teachers).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id")Long id){
        Teacher teacher = teacherService.getATeacherById(id);
        if (teacher == null){
            throw new NotFoundExceptionHandler("TEACHER WITH ID '"+id+"' DOES NOT EXIST! PLEASE TRY WITH VALID ID");
        }
        return Response.ok(teacher).build();
    }

    @GET
    @Path("/lastName/{lastName}")
    public Response getByLastName(@PathParam("lastName")String lastName){
        List<Teacher> teacherList = teacherService.getTeacherByLastName(lastName);

        return Response.ok(teacherList).build();
    }

    @POST
    @Path("")
    public Response create(Teacher teacher){
        teacherService.createTeacher(teacher);
        return Response.ok(teacher).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id")Long id){
        teacherService.delete(id);
        return Response.ok().build();
    }

}
