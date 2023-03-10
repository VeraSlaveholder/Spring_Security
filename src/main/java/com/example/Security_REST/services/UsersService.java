package com.example.Security_REST.services;


import com.example.Security_REST.dao.UserDAO;
import com.example.Security_REST.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UsersService {
    private final UserDAO userDAO;


    @Autowired
    public UsersService(UserDAO userDAO) {
        this.userDAO = userDAO;

    }

    public List<User> findAll() {
        return userDAO.findAll();
    }

    public User findOne(int id) {
        return userDAO.findById(id);
    }

    @Transactional
    public void save(User user) {
        userDAO.save(user);
    }

    @Transactional
    public void update(User updatedUser) {
        userDAO.update(updatedUser);

    }

    @Transactional
    public void delete(int id) {
        userDAO.delete(id);
    }

    public Optional<User> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }
}
