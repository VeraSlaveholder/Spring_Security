package com.example.Security_REST.dao;

import com.example.Security_REST.models.Users;

import java.util.List;

public interface UserDAO {

    List<Users> findAll();

    Users findById(int id);

    void save(Users user);

    void update(Users updatedUser);

    void delete(int id);

    Users findByUsername(String name);
}
