package com.example.Security_REST.dao;

import com.example.Security_REST.models.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Role getRoleByName(String name) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.roleName = :roleName", Role.class)
                .setParameter("roleName", name)
                .setMaxResults(1)
                .getSingleResult();
    }

    public Role getDefaultRole() {
        return getRoleByName("ROLE_USER");
    }

    public Role getAdminRole() {
        return getRoleByName("ROLE_ADMIN");
    }
    public List<Role> listAllRoles() {
        return entityManager.createQuery("from Role", Role.class
        ).getResultList();
    }
}
