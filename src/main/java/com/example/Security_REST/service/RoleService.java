package com.example.Security_REST.service;


import com.example.Security_REST.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> findAllRole();

    Set<Role> findByIdRoles(List<Integer> roles);
}
