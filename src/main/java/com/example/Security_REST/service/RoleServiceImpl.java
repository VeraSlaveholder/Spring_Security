package com.example.Security_REST.service;

import com.example.Security_REST.DAO.RoleRepository;
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

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Set<Role> findByIdRoles(List<Integer> roles) {
        return new HashSet<>(roleRepository.findAllById(roles));
    }
}
