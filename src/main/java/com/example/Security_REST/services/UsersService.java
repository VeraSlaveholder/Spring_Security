package com.example.Security_REST.services;

import com.example.Security_REST.dao.UserDAO;
import com.example.Security_REST.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UsersService implements UserDetailsService {


    private final UserDAO userDAO;


    @Autowired
    public UsersService(UserDAO userDAO) {
        this.userDAO = userDAO;

    }

    public List<Users> findAll() {
        return userDAO.findAll();
    }

    public Users findOne(int id) {
        return userDAO.findById(id);
    }

    @Transactional
    public void save(Users users) {
        userDAO.save(users);
    }

    @Transactional
    public void update(Users updatedUsers) {
        userDAO.update(updatedUsers);

    }

    @Transactional
    public void delete(int id) {
        userDAO.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        Users user = userDAO.findByUsername(name);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown user: " + name);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getAuthorities());
    }
}
