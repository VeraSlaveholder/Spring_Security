package com.example.Security_REST.service;

import com.example.Security_REST.DAO.RoleDAO;
import com.example.Security_REST.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public List<Role> findAllRole() {
        return roleDAO.findAllRoles();
    }

//    @Override
//    @PostConstruct
//    public void addDefaultRole() {
//        roleDAO.addRole(new Role("ROLE_USER"));
//        roleDAO.addRole(new Role("ROLE_ADMIN"));
//    }

    @Override
    public Set<Role> findByIdRoles(List<Integer> roles) {
        return new HashSet<>(roleDAO.findAllById(roles));
    }
}
