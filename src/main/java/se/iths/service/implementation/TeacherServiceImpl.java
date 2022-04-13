package se.iths.service.implementation;

import se.iths.entity.Subject;
import se.iths.entity.Teacher;
import se.iths.exception.NotFoundExceptionHandler;
import se.iths.service.services.TeacherService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Transactional
public class TeacherServiceImpl implements TeacherService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Teacher> getAllTeacher() {
        return entityManager
                .createQuery("SELECT t FROM Teacher t", Teacher.class)
                .getResultList();
    }

    @Override
    public Teacher getATeacherById(Long id) {
        return entityManager.find(Teacher.class, id);

    }

    @Override
    public List<Teacher> findByLasName(String lastName) {

        String query = "SELECT t FROM Teacher t WHERE t.lastName =?1";

        return entityManager
                .createQuery(query, Teacher.class)
                .setParameter(1, lastName)
                .getResultList();

    }

    @Override
    public void createTeacher(Teacher teacher) {
        entityManager.persist(teacher);
    }

    @Override
    public void updateAll(Teacher teacher) {
        entityManager.merge(teacher);
    }

    @Override
    public Teacher updateTeacherFields(Long id, Map<String, Object> fields) {


        Teacher teacher = entityManager.find(Teacher.class, id);
        fields.forEach((key, value) -> {

            switch (key) {
                case "firstName" -> teacher.setFirstName((String) value);
                case "lastName" -> teacher.setLastName((String) value);
                case "email" -> teacher.setEmail((String) value);
                case "phoneNumber" -> teacher.setPhoneNumber((String) value);
            }
        });

        return teacher;
    }


    @Override
    public Teacher updateTeacherFirstName(Long id, String name) {
        Teacher teacher = getATeacherById(id);

        if (teacher == null) {
            throw new NotFoundExceptionHandler("ID '" + id + "'IS NOT VALID ID! PLEASE TRY WITH VALID ID!");
        }

        if (name == null || name.isEmpty()) {
            throw new NotFoundExceptionHandler("NAME CAN NOT BE NULL OR EMPTY!");
        }

        teacher.setFirstName(name);
        return teacher;
    }

    @Override
    public void deleteTeacher(Long id) {

        Teacher teacher = entityManager.find(Teacher.class, id);

        for (Subject subject: teacher.getSubjects()){
            subject.removeTeacherFromSubject(teacher);
        }

        checkIfTeacherIdExist(id, teacher);
        entityManager.remove(teacher);
    }

    @Override
    public void createNewTeacherWithSubject(Long subjectid, Teacher teacher) {
        Subject subject = entityManager.find(Subject.class, subjectid);
        if (subject == null) {
            throw new NotFoundExceptionHandler("SUBJECT WITH ID '" + subjectid + "' DOES NOT FOUND! TRY WITH VALID ID");
        }
        entityManager.persist(teacher);
        teacher.addSubjectToTeacher(subject);
    }

    @Override
    public List<Teacher> existByEmail() {
        return entityManager.createQuery("SELECT t.email FROM Teacher t", Teacher.class).getResultList();
    }

    @Override
    public List<Teacher> existByLastName() {
        return entityManager.createQuery("SELECT t.lastName FROM Teacher t", Teacher.class).getResultList();
    }

    @Override
    public Teacher updateEmail(Long id, String email) {
        Teacher teacher = entityManager.find(Teacher.class, id);
        teacher.setEmail(email);
        return teacher;
    }

    @Override
    public Teacher updateTeacher(Long id, String firstName, String lastName, String email, String phone) {
        Teacher teacher = entityManager.find(Teacher.class, id);
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setEmail(email);
        teacher.setPhoneNumber(phone);
        return teacher;
    }

    private void checkIfTeacherIdExist(Long id, Teacher teacher) {
        if (teacher == null) {
            throw new NotFoundExceptionHandler("ID '" + id + "'IS NOT VALID STUDENT ID! PLEASE TRY WITH VALID ID!");
        }
    }

}
