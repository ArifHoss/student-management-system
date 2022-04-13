package se.iths.service;


import se.iths.entity.Student;
import se.iths.exception.ConflictExceptionHandler;
import se.iths.exception.NotFoundExceptionHandler;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Transactional
public class StudentServiceImpl implements StudentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Student> getAllStudent() {

        return entityManager
                .createQuery("select s from Student s", Student.class)
                .getResultList();
    }

    @Override
    public void createAStudent(Student student) {

        entityManager.persist(student);

    }

    @Override
    public Student getAStudentById(Long id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    public void updateAllStudent(Student student) {
        entityManager.merge(student);
    }

    @Override
    public Student updateStudentFields(Long id, Map<String, Object> fields) {

        Student student = entityManager.find(Student.class, id);

        fields.forEach((key, value) -> {

            setStudentValue(student, key, value);
        });
        return student;
    }

    @Override
    public List<Student> findByLastName(String lastName) {
        String query = "SELECT s FROM Student s WHERE s.lastName =?1";

        return entityManager.createQuery(query, Student.class)
                .setParameter(1, lastName)
                .getResultList();
    }

    @Override
    public List<Student> findByLastNameQuery(String lastName) {
        String query = "SELECT s FROM Student s WHERE s.lastName = :lastName";
        return entityManager.createQuery(query, Student.class).setParameter("lastName", lastName).getResultList();
    }

    @Override
    public void deleteAStudent(Long id) {
        Student student = entityManager.find(Student.class, id);
        entityManager.remove(student);
    }

    @Override
    public List<Student> existByEmail() {
        return entityManager.createQuery("SELECT s.email FROM Student s", Student.class).getResultList();

    }

    @Override
    public List<Student> existByLastName() {
        return entityManager.createQuery("SELECT s.lastName FROM Student s", Student.class).getResultList();
    }

    @Override
    public Student updateStudentFirstName(Long id, String firstName) {
        Student student = entityManager.find(Student.class, id);

        if (student == null) {
            throw new NotFoundExceptionHandler("ID '" + id + "'IS NOT VALID STUDENT ID! PLEASE TRY WITH VALID ID!");
        }

        if (firstName == null || firstName.isEmpty()){
            throw new NotFoundExceptionHandler("STUDENT NAME CAN NOT BE NULL OR EMPTY!");
        }
        student.setFirstName(firstName);

        return student;
    }

    private void setStudentValue(Student student, String key, Object value) {

        String email = student.getEmail();

        if (key.equals("firstName")) {

            student.setFirstName((String) value);
        }
        if (key.equals("lastName")) {
            student.setLastName((String) value);
        }
        if (key.equals("email")) {
            if (existByEmail().contains(value)) {
                throw new ConflictExceptionHandler("EMAIL '" + email + "' ALREADY EXIST! TRY WITH ANOTHER EMAIL");
//                throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
//                        .entity("EMAIL_" + email + "_ALREADY_EXIST!TRY_WITH_ANOTHER_EMAIL").build());
            }
            student.setEmail((String) value);
        }
        if (key.equals("phoneNumber")) {
            student.setPhoneNumber((String) value);
        }
//            switch (key) {
//                case "firstName" -> student.setFirstName((String) value);
//                case "lastName" -> student.setLastName((String) value);
//                case "email" -> student.setEmail((String) value);
//                case "phoneNumber" -> student.setPhoneNumber((String) value);
//            }
    }


}
