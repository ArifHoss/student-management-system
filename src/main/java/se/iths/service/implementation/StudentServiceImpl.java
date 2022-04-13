package se.iths.service.implementation;


import se.iths.entity.Student;
import se.iths.entity.Subject;
import se.iths.exception.ConflictExceptionHandler;
import se.iths.exception.NotFoundExceptionHandler;
import se.iths.service.services.StudentService;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Transactional
public class StudentServiceImpl implements StudentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Student> getAllStudent() {

        return entityManager
                .createQuery("select s from Student s ", Student.class)
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

        return entityManager
                .createQuery(query, Student.class)
                .setParameter(1, lastName)
                .getResultList();
    }

    @Override
    public void deleteAStudent(Long id) {
        Student student = entityManager.find(Student.class, id);

        for (Subject subject: student.getSubjects()){
            student.removeSubjectFromStudent(subject);
        }

        isStudentIdExist(id,student);
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

        isStudentIdExist(id, student);

        if (firstName == null || firstName.isEmpty()) {
            throw new NotFoundExceptionHandler("STUDENT NAME CAN NOT BE NULL OR EMPTY!");
        }
        student.setFirstName(firstName);

        return student;
    }


    @Override
    public Student createNewStudentWithSubject(Long subjectid, Student student) {
        Subject subject = entityManager.find(Subject.class, subjectid);
        entityManager.persist(student);
        student.getSubjects().add(subject);
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
            }
            student.setEmail((String) value);
        }
        if (key.equals("phoneNumber")) {
            student.setPhoneNumber((String) value);
        }

    }


    private void isStudentIdExist(Long id, Student student) {
        if (student == null) {
            throw new NotFoundExceptionHandler("ID '" + id + "'IS NOT VALID STUDENT ID! PLEASE TRY WITH VALID ID!");
        }
    }


}
