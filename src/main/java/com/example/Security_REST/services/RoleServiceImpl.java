package com.example.Security_REST.services;

import com.example.Security_REST.dao.RoleDAOImpl;
import com.example.Security_REST.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDAOImpl roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAOImpl roleDAO) {
        this.roleDAO = roleDAO;
    }


    @Override
    public List<Role> getAllRoles() {
        return roleDAO.getAllRoles();
    }

    @Override
    public Role getRole(String userRole) {
        return roleDAO.getRole(userRole);
    }

    @Override
    public Role getRoleById(int id) {
        return roleDAO.getRoleById(id);
    }

    @Override
    @Transactional
    public void addRole(Role role) {
        roleDAO.addRole(role);
    }
//    @PostConstruct
//    public void addDefaultRole() {
//        roleDAO.addRole(new Role("ROLE_USER"));
//        roleDAO.addRole(new Role("ROLE_ADMIN"));
//    }
}
