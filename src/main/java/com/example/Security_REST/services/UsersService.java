package com.example.Security_REST.services;

import com.example.Security_REST.models.Users;

import java.util.List;

public interface UsersService {
    List<Users> findAll();

    Users findOne(int id);

    void save(Users user);

    void update(Users updatedUser);

    void delete(int id);

    Users findByUsername(String name);
}
