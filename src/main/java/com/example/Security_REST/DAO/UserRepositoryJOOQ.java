package com.example.Security_REST.DAO;

import com.example.Security_REST.model.User;

import java.util.List;

public interface UserRepositoryJOOQ {
    List<User> findAll();

    User getById(int id);

    void save(User user);

    void deleteById(int id);

    User findByUsername(String name);

    void update(User user);
}
