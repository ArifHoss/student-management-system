package se.iths.rest;


import se.iths.entity.Teacher;
import se.iths.exception.ConflictExceptionHandler;
import se.iths.exception.NotFoundExceptionHandler;
import se.iths.service.services.TeacherService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

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

        List<Teacher> teacherList = teacherService.findByLasName(lastName);
        List<Teacher> existByLastName = teacherService.existByLastName();
        if (!existByLastName.contains(lastName)){
            throw new NotFoundExceptionHandler("LAST NAME '" + lastName + "' NOT EXIST IN STUDENT DATABASE! PLEASE WRITE CORRECT LASTNAME");
        }

        return Response.ok(teacherList).build();
    }

    @POST
    @Path("")
    public Response create(Teacher teacher){
        checkTeacherValue(teacher);
        teacherService.createTeacher(teacher);
        return Response.ok(teacher).build();
    }

    @POST
    @Path("/subject/{subjectid}")
    public Response createNewTeacherWithSubject(@PathParam("subjectid")Long subjectid, Teacher teacher){
        checkTeacherValue(teacher);
        teacherService.createNewTeacherWithSubject(subjectid, teacher);
        return Response.ok(teacher).build();
    }

    @PUT
    @Path("/updateall")
    public Response updateAll(Teacher teacher){
        checkTeacherValue(teacher);
        teacherService.updateAll(teacher);
        return Response.ok(teacher).build();
    }

    @PATCH
    @Path("/firstname/{id}")
    public Response updateFirstName(@PathParam("id")Long id, Teacher teacher){

        String name = teacher.getFirstName();
        Teacher updatedTeacher = teacherService.updateTeacherFirstName(id,name);
        return Response.ok(updatedTeacher).build();
    }

    @PATCH
    @Path("/value/{id}")
    public Response updateTeacherFields(@PathParam("id")Long id, Map<String, Object> fields){
        Teacher teacher = teacherService.getATeacherById(id);
        checkIfTeacherIdExist(id, teacher);
        Teacher updatedTeacher = teacherService.updateTeacherFields(id, fields);

        return Response.ok(updatedTeacher).build();
    }

    @PATCH
    @Path("/update/{id}")
    public Response updateTeacher(@PathParam("id")Long id, Teacher teacher){
        checkIfTeacherIdExist(id, teacher);
        checkTeacherValue(teacher);
        String firstName = teacher.getFirstName();
        String lastName = teacher.getLastName();
        String email = teacher.getEmail();
        String phone = teacher.getPhoneNumber();
        Teacher updatedTeacher = teacherService.updateTeacher(id,firstName, lastName, email, phone);

        return Response.ok(updatedTeacher).build();
    }



    @PATCH
    @Path("/email/{id}")
    public Response updateEmail(@PathParam("id")Long id, Teacher teacher){
        String email = teacher.getEmail();
        if (!isValidEmail(email)){
            throw new NotFoundExceptionHandler("THIS '"+ email+"' IS INVALID EMAIL FORMAT! TRY WITH VALID EMAIL FORMAT!");
        }
        Teacher updatedTeacher = teacherService.updateEmail(id, email);
        return Response.ok(updatedTeacher).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id")Long id){
        teacherService.delete(id);
        return Response.ok("TEACHER HAS BEEN DELETED").build();
    }

    private void checkTeacherValue(Teacher teacher) {

        String firstName = teacher.getFirstName();
        String lastName = teacher.getLastName();
        String email = teacher.getEmail();
        List<Teacher> existEmail = teacherService.existByEmail();

        if (firstName == null||firstName.isEmpty()) {
            throw new NotFoundExceptionHandler("FIRSTNAME CANNOT BE NULL OR EMPTY");
        }

        if (lastName == null ||lastName.isEmpty()) {
            throw new NotFoundExceptionHandler("LASTNAME CANNOT BE NULL OR EMPTY");
        }

        if (!isValidEmail(email)){
            throw new NotFoundExceptionHandler("THIS '"+ email+"' IS INVALID EMAIL FORMAT! TRY WITH VALID EMAIL FORMAT!");
        }

        if (email == null||email.isEmpty()) {
            throw new NotFoundExceptionHandler("EMAIL CANNOT BE NULL OR EMPTY");
        }

        if (existEmail.contains(email)) {
            throw new ConflictExceptionHandler("EMAIL '" + email + "' ALREADY EXIST! TRY WITH ANOTHER EMAIL");
        }
    }

    public boolean isValidEmail(String email) {

        String regex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[_A-Za-z0-9-]+)";
        return email.matches(regex);

    }

    private void checkIfTeacherIdExist(Long id, Teacher teacher) {
        if (teacher == null) {
            throw new NotFoundExceptionHandler("ID '" + id + "'IS NOT VALID STUDENT ID! PLEASE TRY WITH VALID ID!");
        }
    }

}
