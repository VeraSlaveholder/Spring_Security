package com.example.Security_REST.DAO;

import com.example.Security_REST.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    @Query("SELECT r FROM Role r WHERE r.id IN :roles")
    Set<Role> findAllById(@Param("roles") List<Integer> roleIds);
    Role findById(int id);
}
