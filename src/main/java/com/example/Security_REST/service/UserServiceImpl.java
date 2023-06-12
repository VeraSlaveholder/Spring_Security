package com.example.Security_REST.service;

import com.example.Security_REST.DAO.UserDAOJOOQImpl;
import com.example.Security_REST.DAO.UserRepository;
import com.example.Security_REST.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDAOJOOQImpl userDAO;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDAOJOOQImpl userDAO) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
    }

    @Override
    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User getById(int id) {
        return userDAO.getById(id);
    }

    @Override
    public void save(User user) {
        userDAO.save(passwordCoder(user));
    }

    @Override
    public void update(int id, User user) {
        String oldPassword = userDAO.getById(id).getPassword();
        if (oldPassword.equals(user.getPassword())) {
            user.setPassword(oldPassword);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setUserId(id);
        userDAO.update(user);
    }

    @Override
    public void deleteById(int id) {
        userDAO.deleteById(id);
    }

    @Override
    public User findByUsername(Principal principal) {
        if (principal == null) {
            return null;
        }
        String name = principal.getName();
        return userDAO.findByUsername(name);
    }

    @Override
    public String getErrorsFromBindingResult(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
    }
}

