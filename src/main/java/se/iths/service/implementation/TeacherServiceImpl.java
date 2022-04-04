package se.iths.service.implementation;

import se.iths.entity.Teacher;
import se.iths.service.services.TeacherService;

import javax.persistence.EntityManager;
import java.util.List;

public class TeacherServiceImpl implements TeacherService {

    private EntityManager entityManager;

    @Override
    public List<Teacher> getAll() {
        return entityManager
                .createQuery("SELECT t FROM Teacher t",Teacher.class).getResultList();
    }
}
