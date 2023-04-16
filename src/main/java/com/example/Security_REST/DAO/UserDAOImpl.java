package com.example.Security_REST.DAO;

import com.example.Security_REST.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return entityManager.createQuery("SELECT user FROM User user", User.class).getResultList();
    }

    @Transactional(readOnly = true)
    public User getById(int id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    @Transactional
    public void update(User updatedUser) {
        entityManager.merge(updatedUser);
    }

    @Transactional
    public void deleteById(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    public User findByUsername(String name) {
        return entityManager.createQuery("select u from User u where u.name =: name", User.class)
                .setParameter("name", name).getSingleResult();
    }
}