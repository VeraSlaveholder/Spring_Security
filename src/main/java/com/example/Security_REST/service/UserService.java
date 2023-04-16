package com.example.Security_REST.service;


import com.example.Security_REST.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User getById(int id);

    void save(User user);

    void deleteById(int id);

    User findByUsername(String username);

    void addDefaultUser();

    void update(User user);

    User passwordCoder(User user);
}
