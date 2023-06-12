package com.example.Security_REST.service;

import com.example.Security_REST.DAO.RoleRepository;
import com.example.Security_REST.DAO.RoleRepositoryJOOQ;
import com.example.Security_REST.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleRepositoryJOOQ repositoryJOOQ;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, RoleRepositoryJOOQ repositoryJOOQ) {
        this.roleRepository = roleRepository;
        this.repositoryJOOQ = repositoryJOOQ;
    }

    @Override
    public List<Role> findAllRole() {
        return repositoryJOOQ.findAllRoles();
    }

    @Override
    public Set<Role> findByIdRoles(List<Integer> roles) {
        return new HashSet<>(repositoryJOOQ.findAllById(roles));
    }
}
