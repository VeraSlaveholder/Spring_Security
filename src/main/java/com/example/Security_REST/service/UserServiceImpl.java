package com.example.Security_REST.service;

import com.example.Security_REST.DAO.RoleDAO;
import com.example.Security_REST.DAO.RoleDAOImpl;
import com.example.Security_REST.DAO.UserDAO;
import com.example.Security_REST.DAO.UserDAOImpl;
import com.example.Security_REST.model.Role;
import com.example.Security_REST.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAOImpl userDAO, RoleDAOImpl roleDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
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
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public void deleteById(int id) {
        userDAO.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    @PostConstruct
    public void addDefaultUser() {
        Set<Role> roles1 = new HashSet<>();
        roles1.add(roleDAO.findById(1));
        Set<Role> roles2 = new HashSet<>();
        roles2.add(roleDAO.findById(1));
        roles2.add(roleDAO.findById(2));
        User user1 = new User("user", "Jobs", 25, "user@mail.com", "user", roles1);
        User user2 = new User("admin", "Potter", 30, "admin@mail.com", "admin", roles2);
        save(user1);
        save(user2);
    }
}

