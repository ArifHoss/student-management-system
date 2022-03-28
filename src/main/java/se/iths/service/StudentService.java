package se.iths.service;


import se.iths.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
@Transactional
public class StudentService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Student> getAllStudent() {

        List<Student> students = entityManager
                .createQuery("select s from Student s", Student.class)
                .getResultList();

        return students;
    }
}
