package com.example.Security_REST.init;

import com.example.Security_REST.DAO.RoleRepository;
import com.example.Security_REST.model.Role;
import com.example.Security_REST.model.User;
import com.example.Security_REST.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class Start {
    private final RoleRepository roleRepository;
    private final UserService userService;

    @Autowired
    public Start(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }
//
//    @PostConstruct
//    public void addDefaultRole() {
//        roleRepository.save(new Role("ROLE_USER"));
//        roleRepository.save(new Role("ROLE_ADMIN"));
//    }
//
//    @PostConstruct
//    public void addDefaultUser() {
//        Set<Role> roles1 = new HashSet<>();
//        roles1.add(roleRepository.findById(1));
//        Set<Role> roles2 = new HashSet<>();
//        roles2.add(roleRepository.findById(1));
//        roles2.add(roleRepository.findById(2));
//        User user1 = new User("user", "Jobs", 25, "user@mail.com", "user", roles1);
//        User user2 = new User("admin", "Potter", 30, "admin@mail.com", "admin", roles2);
//        userService.save(user1);
//        userService.save(user2);
//    }
}
