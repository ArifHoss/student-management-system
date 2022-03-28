package se.iths.rest;

import se.iths.entity.Student;
import se.iths.service.StudentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/student")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentRest {

    @Inject
    private StudentService studentService;

    @GET
    @Path("")
    public Response getAllStudent() {
        List<Student> students = studentService.getAllStudent();
        return Response.ok(students).build();
    }


    @GET
    @Path("{id}")
    public Response getAllStudent(@PathParam("id") Long id) {
        Student foundStudent = studentService.getAStudentById(id);
        if (foundStudent == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("ID_'" + id + "'_IS_NOT_VALID_STUDENT_ID!PLEASE_TRY_WITH_VALID_ID!").build());
        }
        return Response.ok(foundStudent).build();
    }


    @POST
    @Path("")
    public Response createAStudent(Student student) {
        studentService.createAStudent(student);
        return Response.ok(student).build();
    }

    @PUT
    @Path("")
    public Response updateAllStudent(Student student){
        studentService.updateAllStudent(student);
        return Response.ok(student).build();
    }

    @PATCH
    @Path("/update/{id}")
    public Response updateStudentValue(@PathParam("id") Long id){

//        Student updatedStudent = studentService.updateFName(id, student.getFirstName());
        Student updatedStudent = studentService.updateStudentValue(id);
        return Response.ok(updatedStudent).build();
    }

}
