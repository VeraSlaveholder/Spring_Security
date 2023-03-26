package com.example.Security_REST.services;

import com.example.Security_REST.dao.RoleDAO;
import com.example.Security_REST.dao.UserDAO;
import com.example.Security_REST.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {
    private final RoleDAO roleDAO;

    @Autowired
    public RoleService(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }
    @Transactional
    public Role getRoleByName(String name) {
        return roleDAO.getRoleByName(name);
    }

    public Role getDefaultRole() {
        return roleDAO.getDefaultRole();
    }

    public Role getAdminRole() {
        return roleDAO.getAdminRole();
    }
    public List<Role> getAllRoles() {
        return roleDAO.listAllRoles();
    }

}
