package com.example.Security_REST.services;

import com.example.Security_REST.dao.RoleDAO;
import com.example.Security_REST.dao.UserDAO;
import com.example.Security_REST.models.Role;
import com.example.Security_REST.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Transactional()
@Service
public class UsersServiceImpl implements UserDetailsService, UsersService {
    private final UserDAO userDAO;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    @Lazy
    @Autowired
    public UsersServiceImpl(UserDAO userDAO, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public Users passwordCoder(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
    public List<Users> findAll() {
        return userDAO.findAll();
    }

    public Users findOne(int id) {
        return userDAO.findById(id);
    }

    @Transactional
    public void save(Users user) {
        userDAO.save(passwordCoder(user));
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
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            return userDAO.findByUsername(s);
        } catch (UsernameNotFoundException u) {
            throw new UsernameNotFoundException("user not found");
        }
    }

    @PostConstruct
    public void addDefaultUser() {

        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        Set<Role> adminSet = new HashSet<>();
        Set<Role> userSet = new HashSet<>();

        roleService.addRole(roleAdmin);
        roleService.addRole(roleUser);

        adminSet.add(roleAdmin);
        adminSet.add(roleUser);
        userSet.add(roleUser);

        Users newUser = new Users("user","Ivanov", 23,  "ivan@mail.com", "user", userSet,"admin");
        Users admin = new Users("aa",  "Potter",30, "garry@gmail.com", "aa", adminSet,"aa");

        save(newUser);
        save(admin);
    }

}

