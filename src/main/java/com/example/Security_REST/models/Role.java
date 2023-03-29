package com.example.Security_REST.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Role")
public class Role implements GrantedAuthority {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Item name should not be empty")
    @Column(name = "roleName")
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<Users> usersList = new ArrayList<>();

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return roleName;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}