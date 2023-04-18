package com.example.Security_REST.DAO;

import com.example.Security_REST.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class RoleDAOImpl implements RoleDAO {
    @PersistenceContext
    private EntityManager entityManager;


    public List<Role> findAllRoles() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }

    public Set<Role> findAllById(List<Integer> roles) {
        TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r WHERE r.id IN :roles", Role.class);
        query.setParameter("roles", roles);
        List<Role> resultList = query.getResultList();
        return new HashSet<>(resultList);
    }

    public void addRole(Role role) {
        entityManager.persist(role);
    }


    public Role findById(int id) {
        return entityManager.find(Role.class, id);
    }

}
