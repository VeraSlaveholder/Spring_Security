package com.example.Security_REST.DAO;

import com.example.Security_REST.model.Role;
import org.jooq.Condition;

import java.util.List;
import java.util.Set;

public interface RoleRepositoryJOOQ {

    List<Role> findAllRoles();

    Set<Role> findAllById(List<Integer> roles);

    void addRole(Role role);
    List<Role> findAll(Condition condition);

    Role findById(int id);
}
