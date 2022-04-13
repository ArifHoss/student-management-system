package se.iths.service;

import se.iths.entity.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {
    List<Student> getAllStudent();

    void createAStudent(Student student);

    Student getAStudentById(Long id);

    void updateAllStudent(Student student);

    Student updateStudentFields(Long id, Map<String, Object> fields);

    List<Student> findByLastName(String lastName);
    List<Student> findByLastNameQuery(String lastName);

    void deleteAStudent(Long id);

    List<Student> existByEmail();

    List<Student> existByLastName();

    Student updateStudentFirstName(Long id, String name);
}
