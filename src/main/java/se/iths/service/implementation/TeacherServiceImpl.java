package se.iths.service.implementation;

import se.iths.entity.Teacher;
import se.iths.service.services.TeacherService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class TeacherServiceImpl implements TeacherService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Teacher> getAllTeacher() {
        return entityManager
                .createQuery("SELECT t FROM Teacher t",Teacher.class)
                .getResultList();
    }

    @Override
    public Teacher getATeacherById(Long id) {
        return entityManager.find(Teacher.class, id);

    }

    @Override
    public List<Teacher> getTeacherByLastName(String lastName) {

        String searchByLastName = "SELECT t FROM Teacher t WHERE t.lastName =?1";

        return entityManager
                .createQuery(searchByLastName, Teacher.class)
                .setParameter(1,lastName)
                .getResultList();

    }

    @Override
    public void createTeacher(Teacher teacher) {
        entityManager.persist(teacher);
    }

    @Override
    public void delete(Long id) {
        Teacher teacher = entityManager.find(Teacher.class, id);
        entityManager.remove(teacher);

    }
}
