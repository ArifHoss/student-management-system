package se.iths.service.services;

import se.iths.entity.Teacher;

import java.util.List;
import java.util.Map;

public interface TeacherService {

    List<Teacher> getAllTeacher();

    Teacher getATeacherById(Long id);

    List<Teacher> findByLasName(String lastName);

    void createTeacher(Teacher teacher);

    void updateAll(Teacher teacher);

    Teacher updateTeacherFields(Long id, Map<String, Object> fields);

    Teacher updateTeacherFirstName(Long id, String name);

    void delete(Long id);

    void createNewTeacherWithSubject(Long subjectid, Teacher teacher);

    List<Teacher> existByEmail();

    List<Teacher> existByLastName();

    Teacher updateEmail(Long id, String email);

    Teacher updateTeacher(Long id, String firstName, String lastName, String email, String phone);
}
