package se.iths.service.services;

import se.iths.entity.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {

    List<Student> getAllStudent();

    Student getAStudentById(Long id);

    void createAStudent(Student student);

    void updateAllStudent(Student student);

    Student updateStudentFields(Long id, Map<String, Object> fields);

    List<Student> findByLastName(String lastName);

    void deleteAStudent(Long id);

    List<Student> existByEmail();

    List<Student> existByLastName();

    Student updateStudentFirstName(Long id, String name);

    Student createNewStudentWithSubject(Long subjectid, Student student);
}
