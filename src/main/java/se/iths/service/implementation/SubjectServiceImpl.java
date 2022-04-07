package se.iths.service.implementation;

import se.iths.entity.Student;
import se.iths.entity.Subject;
import se.iths.entity.Teacher;
import se.iths.exception.NotFoundExceptionHandler;
import se.iths.service.services.StudentService;
import se.iths.service.services.SubjectService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class SubjectServiceImpl implements SubjectService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Subject> getAllSubject() {

        return entityManager
                .createQuery("SELECT s FROM Subject s", Subject.class)
                .getResultList();
    }

    @Override
    public Subject getSubjectById(Long id) {
        return entityManager.find(Subject.class, id);
    }

    @Override
    public void createSubject(Subject subject) {
        entityManager.persist(subject);
    }

    @Override
    public void updateAllSubject(Subject subject) {
        entityManager.merge(subject);

    }

    @Override
    public Subject updateSubjectName(Long id, String name) {
        Subject subject = entityManager.find(Subject.class, id);
        checkIfIdExist(id, subject);
        subject.setName(name);
        return subject;
    }

    @Override
    public void delete(Long id) {
        Subject subject = entityManager.find(Subject.class, id);
        checkIfIdExist(id, subject);
        entityManager.remove(subject);
    }

    @Override
    public Subject addExistingSubjectToExistingTeacher(Long subjectid, Long teacherid) {
        Teacher teacher = entityManager.find(Teacher.class, teacherid);
        Subject subject = getSubjectById(subjectid);

        subject.setTeacher(teacher);

//        teacher.addSubject(subject);
//        entityManager.persist(subject);
        return subject;
    }

    @Override
    public Subject addExistingSubjectToExistingStudent(Long subjectid, Long studentid) {
        Student student = entityManager.find(Student.class, studentid);
        Subject subject = getSubjectById(subjectid);

        subject.addStudent(student);

//        student.addSubject(subject);
//        entityManager.persist(subject);
        return subject;
    }

    @Override
    public List<Subject> isNameExist(Subject subject) {
        return entityManager.createQuery("SELECT s.name FROM Subject s", Subject.class).getResultList();
    }


    public void checkIfIdExist(Long id, Subject subject) {
        if (subject == null){
            throw new NotFoundExceptionHandler("SUBJECT WITH ID '"+ id +"' DOES NOT EXIST! TRY WITH VALID ID!");
        }
    }
}
