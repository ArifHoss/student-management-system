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

    public void createAStudent(Student student) {
        entityManager.persist(student);
    }

    public Student getAStudentById(Long id) {
        return entityManager.find(Student.class, id);
    }

    public void updateAllStudent(Student student) {
        entityManager.merge(student);
    }

    public Student updateStudentValue(Long id) {
        Student student = entityManager.find(Student.class, id);

        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        String email = student.getEmail();
        String phoneNumber = student.getPhoneNumber();

        if (student.getFirstName().equals(firstName)) {
            student.setFirstName(firstName);
        }
        if (student.getLastName().equals(lastName)) {
            student.setLastName(lastName);
        }
        if (student.getEmail().equals(email)) {
            student.setEmail(email);
        }
        if (student.getPhoneNumber().equals(phoneNumber)) {
            student.setPhoneNumber(phoneNumber);
        } else {
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setEmail(email);
            student.setPhoneNumber(phoneNumber);
        }
        return student;
    }

    public Student updateFName(Long id, String firstName) {
        Student student = entityManager.find(Student.class, id);
        student.setFirstName(firstName);
        return student;
    }
}
