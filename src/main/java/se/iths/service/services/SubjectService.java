package se.iths.service.services;

import se.iths.entity.Subject;

import java.util.List;
import java.util.Map;

public interface SubjectService {

    List<Subject> getAllSubject();

    Subject getSubjectById(Long id);

    void createSubject(Subject subject);

    void updateAllSubject(Subject subject);

    Subject updateSubjectName(Long id, String name);

    void delete(Long id);

    Subject addExistingSubjectToExistingTeacher(Long subjectid, Long teacherid);

    Subject addExistingSubjectToExistingStudent(Long subjectid, Long studentid);

    List<Subject> isNameExist(Subject subject);

}
