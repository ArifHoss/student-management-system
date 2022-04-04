package se.iths.service.implementation;

import se.iths.entity.Subject;
import se.iths.service.services.SubjectService;

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
        return entityManager.find(Subject.class,id);
    }

    @Override
    public void createSubject(Subject subject) {
        entityManager.persist(subject);
    }

    @Override
    public void delete(Long id) {
        Subject subject = entityManager.find(Subject.class, id);
        entityManager.remove(subject);
    }
}
