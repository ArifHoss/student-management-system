package se.iths.service.implementation;

import se.iths.entity.Subject;
import se.iths.service.services.SubjectService;

import javax.persistence.EntityManager;
import java.util.List;

public class SubjectServiceImpl implements SubjectService {
    private EntityManager entityManager;

    @Override
    public List<Subject> getAll() {

        return entityManager
                .createQuery("SELECT s FROM Subject s", Subject.class)
                .getResultList();
    }
}
