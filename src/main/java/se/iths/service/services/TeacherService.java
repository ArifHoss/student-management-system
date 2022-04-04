package se.iths.service.services;

import se.iths.entity.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> getAllTeacher();

    Teacher getATeacherById(Long id);

    List<Teacher> getTeacherByLastName(String lastName);

    void createTeacher(Teacher teacher);

    void delete(Long id);
}
