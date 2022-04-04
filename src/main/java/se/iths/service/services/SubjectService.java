package se.iths.service.services;

import se.iths.entity.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> getAllSubject();

    Subject getSubjectById(Long id);

    void createSubject(Subject subject);

    void delete(Long id);
}
