package com.example.Security_REST.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users owner;

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
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
        return "Item{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}