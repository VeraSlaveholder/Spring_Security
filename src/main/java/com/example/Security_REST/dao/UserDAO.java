package com.example.Security_REST.dao;


import com.example.Security_REST.models.Users;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<Users> findAll() {
        return entityManager.createQuery("SELECT users FROM Users users", Users.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Users findById(int id) {
        return entityManager.find(Users.class, id);
    }

    @Transactional
    public void save(Users user) {
        entityManager.persist(user);
        entityManager.close();
    }

    @Transactional
    public void update(Users updatedUser) {
        entityManager.merge(updatedUser);
    }

    @Transactional
    public void delete(int id) {
        Users user = entityManager.find(Users.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
}