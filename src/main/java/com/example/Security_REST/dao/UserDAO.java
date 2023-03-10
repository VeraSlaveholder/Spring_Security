package com.example.Security_REST.dao;


import com.example.Security_REST.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return entityManager.createQuery("SELECT users FROM Users users", User.class).getResultList();
    }

    @Transactional(readOnly = true)
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    public void save(User user) {
        entityManager.persist(user);
        entityManager.close();
    }

    @Transactional
    public void update(User updatedUser) {
        entityManager.merge(updatedUser);
    }

    @Transactional
    public void delete(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(entityManager.find(User.class, username));
    }
}