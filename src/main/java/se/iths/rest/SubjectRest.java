package se.iths.rest;


import se.iths.entity.Student;
import se.iths.entity.Subject;
import se.iths.entity.Teacher;
import se.iths.exception.ConflictExceptionHandler;
import se.iths.exception.NotFoundExceptionHandler;
import se.iths.service.services.StudentService;
import se.iths.service.services.SubjectService;
import se.iths.service.services.TeacherService;

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
    private final TeacherService teacherService;
    private final StudentService studentService;

    @Inject
    SubjectRest(SubjectService subjectService, TeacherService teacherService, StudentService studentService) {
        this.subjectService = subjectService;
        this.teacherService = teacherService;
        this.studentService= studentService;
    }

    @GET
    @Path("")
    public Response getAllSubject() {
        List<Subject> subjects = subjectService.getAllSubject();
        return Response.ok(subjects).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") Long id) {
        checkSubjectExist(id);
        Subject subject = subjectService.getSubjectById(id);
        return Response.ok(subject).build();
    }

    @POST
    @Path("")
    public Response create(Subject subject) {
        checkSubjectName(subject);
        subjectService.createSubject(subject);
        return Response.ok(subject).build();
    }

    @PUT
    @Path("")
    public Response updateAll(Subject subject){
        checkSubjectName(subject);
        subjectService.updateAllSubject(subject);
        return Response.ok(subject).build();
    }

    @PATCH
    @Path("/name/{id}")
    public Response updateName(@PathParam("id")Long id, Subject subject){
        checkSubjectName(subject);
        checkSubjectExist(id);
        String getName = subject.getName();
        List<Subject> subjectList = subjectService.updateSubjectName(id, getName);
        return Response.ok(subjectList).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        checkSubjectExist(id);
        subjectService.delete(id);
        return Response.ok("SUBJECT HAS BEEN DELETED").build();
    }

    @PUT
    @Path("/{subjectid}/teacher/{teacherid}")
    public Response addExistingSubjectToExistingTeacher(
            @PathParam("subjectid") Long subjectid,
            @PathParam("teacherid") Long teacherid) {

        checkSubjectExist(subjectid);
        Teacher teacher = teacherService.getATeacherById(teacherid);
        if (teacher == null){
            throw new NotFoundExceptionHandler("TEACHER WITH ID '" + teacherid + "' DOES NOT FOUND!PLEASE TRY WITH A VALID ID");
        }
        subjectService.addExistingSubjectToExistingTeacher(subjectid, teacherid);
        return Response.ok("SUBJECT WITH ID '"+subjectid+"' ADDED TO TEACHER ID '"+teacherid).build();
    }

    @PUT
    @Path("/{subjectid}/student/{studentid}")
    public Response addExistingSubjectToExistingStudent(
            @PathParam("subjectid") Long subjectid,
            @PathParam("studentid") Long studentid) {

        checkSubjectExist(subjectid);
        Student student = studentService.getAStudentById(studentid);
        if (student == null){
            throw new NotFoundExceptionHandler("STUDENT WITH ID '" + studentid + "' DOES NOT FOUND!PLEASE TRY WITH A VALID ID");
        }

        subjectService.addExistingSubjectToExistingStudent(subjectid, studentid);
        return Response.ok("SUBJECT WITH ID '"+subjectid+"' ADDED TO STUDENT ID '"+studentid).build();
    }

    private void checkSubjectExist(Long id) {
        Subject subject = subjectService.getSubjectById(id);
        if (subject == null) {
            throw new NotFoundExceptionHandler("SUBJECT WITH ID '" + id + "' DOES NOT FOUND!PLEASE TRY WITH A VALID ID");
        }
    }

    private void checkSubjectName(Subject subject) {
        String name = subject.getName();
        List<Subject> nameExist = subjectService.isNameExist(subject);
        if (name == null || name.isEmpty()){
            throw new NotFoundExceptionHandler("NAME CAN NOT BE EMPTY OR NULL");
        }
        if (nameExist.contains(name)){
            throw new ConflictExceptionHandler("NAME ALREADY EXIST! TRY WITH DIFFERENT NAME");
        }
    }
}
