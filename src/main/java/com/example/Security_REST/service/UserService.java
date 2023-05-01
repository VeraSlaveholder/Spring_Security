package com.example.Security_REST.service;


import com.example.Security_REST.model.User;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.List;

public interface UserService {

    List<User> findAll();

    User getById(int id);

    void save(User user);

    void deleteById(int id);

    User findByUsername(Principal principal);

    void update(int id, User user);

    User passwordCoder(User user);

    String getErrorsFromBindingResult(BindingResult bindingResult);
}
