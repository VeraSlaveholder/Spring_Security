package com.example.Security_REST.dao;

import com.example.Security_REST.models.Users;

import java.util.List;

public interface UserDAO {

    public List<Users> findAll();

    public Users findById(int id);

    public void save(Users user);

    public void update(Users updatedUser);

    public void delete(int id);

    public Users findByUsername(String name);
}
