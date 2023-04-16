package com.example.Security_REST.DAO;


import com.example.Security_REST.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDAO {
    List<Role> findAllRoles();

    Set<Role> findAllById(List<Integer> roles);

    void addRole(Role role);

    Role findById(int id);
}
