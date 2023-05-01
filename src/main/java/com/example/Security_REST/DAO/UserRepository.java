package com.example.Security_REST.DAO;

import com.example.Security_REST.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User getUserByUserId(int id);
    void deleteByUserId(int id);
    User findByName(String name);
}
