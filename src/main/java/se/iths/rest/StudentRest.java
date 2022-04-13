package se.iths.rest;

import se.iths.entity.Student;
import se.iths.exception.ConflictExceptionHandler;
import se.iths.exception.NotFoundExceptionHandler;
import se.iths.service.StudentService;

import javax.inject.Inject;
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
        checkIfStudentIdExist(id, student);

        return Response.ok(student).build();
    }

    @GET
    @Path("/lastName/{lastName}")
    public Response findStudentByLastName(@PathParam("lastName") String lastName) {

        List<Student> student = studentService.findByLastName(lastName);
        List<Student> existByLastName = studentService.existByLastName();
        if (!existByLastName.contains(lastName)) {
            throw new NotFoundExceptionHandler("LAST NAME '" + lastName + "' NOT EXIST IN STUDENT DATABASE! PLEASE WRITE CORRECT LASTNAME");
        }

        return Response.ok(student).build();
    }

    @GET
    @Path("/lastname")
    public Response findStudentByLastNameQuery(@QueryParam("lastName") String lastName) {

        List<Student> student = studentService.findByLastNameQuery(lastName);
        List<Student> existByLastName = studentService.existByLastName();
        if (!existByLastName.contains(lastName)) {
            throw new NotFoundExceptionHandler("LAST NAME '" + lastName + "' NOT EXIST IN STUDENT DATABASE! PLEASE WRITE CORRECT LASTNAME");
        }

        return Response.ok(student).build();
    }

    @POST
    @Path("")
    public Response createAStudent(Student student) {

        checkStudentValue(student);
        studentService.createAStudent(student);

        return Response.ok(student).build();
    }

    @PUT
    @Path("")
    public Response updateAllStudent(Student student) {

        checkStudentValue(student);
        studentService.updateAllStudent(student);

        return Response.ok(student).build();
    }

    @PATCH
    @Path("/value/{id}")
    public Response updateStudentFields(@PathParam("id") Long id, Map<String, Object> fields) {

        Student student = studentService.getAStudentById(id);
        checkIfStudentIdExist(id, student);
        Student getStudentFields = studentService.updateStudentFields(id, fields);

        return Response.ok(getStudentFields).build();

    }

    @PATCH
    @Path("/firstname/{id}")
    public Response updateStudentFirstName(@PathParam("id")Long id, Student student){
        String firstName = student.getFirstName();
        Student updateFirstName = studentService.updateStudentFirstName(id, firstName);
        return Response.ok(updateFirstName).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAStudent(@PathParam("id") Long id) {

        Student student = studentService.getAStudentById(id);
        checkIfStudentIdExist(id, student);
        studentService.deleteAStudent(id);

        return Response.ok("STUDENT WITH ID '" + id + "' DELETED!").build();
    }

    private void checkStudentValue(Student student) {

        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        String email = student.getEmail();
        List<Student> existEmail = studentService.existByEmail();

        if (firstName == null||firstName.isEmpty()) {
            throw new NotFoundExceptionHandler("FIRSTNAME CANNOT BE NULL OR EMPTY");
        }
        if (lastName == null ||lastName.isEmpty()) {
            throw new NotFoundExceptionHandler("LASTNAME CANNOT BE NULL OR EMPTY");
        }

        if (email == null||email.isEmpty()) {
            throw new NotFoundExceptionHandler("EMAIL CANNOT BE NULL OR EMPTY");
        }
        if (existEmail.contains(email)) {
            throw new ConflictExceptionHandler("EMAIL '" + email + "' ALREADY EXIST! TRY WITH ANOTHER EMAIL");
        }
    }

    private void checkIfStudentIdExist(Long id, Student student) {
        if (student == null) {
            throw new NotFoundExceptionHandler("ID '" + id + "'IS NOT VALID STUDENT ID! PLEASE TRY WITH VALID ID!");
        }
    }
}
