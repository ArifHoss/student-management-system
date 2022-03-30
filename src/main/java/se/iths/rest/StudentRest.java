package se.iths.rest;

import se.iths.entity.Student;
import se.iths.service.StudentService;

import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.RequestMap;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/student")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentRest {

    private final StudentService studentService;

    @Inject
    public StudentRest(StudentService studentService) {
        this.studentService = studentService;
    }

    @GET
    @Path("")
    public Response getAllStudent() {
        List<Student> students = studentService.getAllStudent();
        return Response.ok(students).build();
    }


    @GET
    @Path("{id}")
    public Response getStudentById(@PathParam("id") Long id) {
        Student student = studentService.getAStudentById(id);
        if (student == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("ID_'" + id + "'_IS_NOT_VALID_STUDENT_ID!PLEASE_TRY_WITH_VALID_ID!").build());
        }
        return Response.ok(student).build();
    }

    @GET
    @Path("/lastName/{lastName}")
    public Response findStudentByLastName(@PathParam("lastName") String lastName) {
        List<Student> student = studentService.findByLastName(lastName);
        List<Student> existByLastName = studentService.existByLastName();
        if (!existByLastName.contains(lastName)) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("LASTNAME_" + lastName + "_NOT_EXIST").build());
        }
        return Response.ok(student).build();
    }

    @POST
    @Path("")
    public Response createAStudent(Student student) {


        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        String email = student.getEmail();
        List<Student> existEmail = studentService.existByEmail();

        if(firstName ==null){
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("FIRSTNAME_CANNOT_BE_NULL").build());
        }
        if(lastName ==null){
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("LASTNAME_CANNOT_BE_NULL").build());
        }

        if(email==null){
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("EMAIL_CANNOT_BE_NULL").build());
        }
        if (existEmail.contains(email)) {
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
                    .entity("EMAIL_" + email + "_ALREADY_EXIST!TRY_WITH_ANOTHER_EMAIL").build());
        }
        studentService.createAStudent(student);

        return Response.ok(student).build();
    }

    @PUT
    @Path("")
    public Response updateAllStudent(Student student) {
        studentService.updateAllStudent(student);
        return Response.ok(student).build();
    }


    @PATCH
    @Path("/value/{id}")
    public Response updateStudentFields(@PathParam("id") Long id, Map<String, Object> fields) {

        Student student = studentService.getAStudentById(id);

        if (student == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("ID_'" + id + "'_IS_NOT_VALID_STUDENT_ID!PLEASE_TRY_WITH_VALID_ID!").build());
        }

        Student getStudentFields = studentService.updateStudentFields(id, fields);

        return Response.ok(getStudentFields).build();

    }

    @DELETE
    @Path("/{id}")
    public Response deleteAStudent(@PathParam("id")Long id){

        Student student = studentService.getAStudentById(id);


        if (student == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("ID_'" + id + "'_IS_NOT_VALID_STUDENT_ID!PLEASE_TRY_WITH_VALID_ID!").build());
        }


        studentService.deleteAStudent(id);
        return Response.ok("STUDENT_WITH_ID_"+id+"_DELETED").build();
    }
}
