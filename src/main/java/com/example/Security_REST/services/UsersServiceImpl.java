package com.example.Security_REST.services;

import com.example.Security_REST.dao.UserDAO;
import com.example.Security_REST.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UsersServiceImpl implements UserDetailsService, UsersService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    public UsersServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Users> findAll() {
        return userDAO.findAll();
    }

    public Users findOne(int id) {
        return userDAO.findById(id);
    }

    @Transactional
    public void save(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.save(user);
    }

    @Transactional
    public void update(Users updatedUser) {
        if (!updatedUser.getPassword().equals(userDAO.findById(updatedUser.getId()).getPassword())) {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        userDAO.update(updatedUser);

    }

    @Transactional
    public void delete(int id) {
        userDAO.delete(id);
    }

    @Transactional
    public Users findByUsername(String name) {
        return userDAO.findByUsername(name);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userDAO.findByUsername(name);
    }
}
