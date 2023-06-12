package com.example.Security_REST.mapper;

import com.example.Security_REST.model.Role;
import org.jooq.Converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RolesConverter implements Converter<Object, Set<Role>> {

    @Override
    public Set<Role> from(Object databaseObject) {
        if (databaseObject == null) {
            return null;
        }
        Set<Role> roles = new HashSet<>();

// Предположим, что databaseObject представляет собой одну строку
        if (databaseObject instanceof String) {
            String roleName = (String) databaseObject;
            Role role = new Role(roleName);
            roles.add(role);
        }

        return roles;
    }

    @Override
    public Object to(Set<Role> userObject) {
        if (userObject == null) {
            return null;
        }
// Создаем список строк для хранения имен ролей
        List<String> roleNames = new ArrayList<>();

        for (Role role : userObject) {
            roleNames.add(role.getRole());
        }

        return roleNames.toArray(new String[0]);
    }

    @Override
    public Class<Object> fromType() {
        // Возвращаем тип базы данных (Object)
        return Object.class;
    }

    @Override
    public Class<Set<Role>> toType() {
        // Возвращаем тип пользователя (Set<Role>)
        return (Class<Set<Role>>) (Class<?>) Set.class;
    }
}