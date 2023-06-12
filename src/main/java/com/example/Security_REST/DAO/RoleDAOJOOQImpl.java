package com.example.Security_REST.DAO;

import com.example.Security_REST.model.Role;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.Security_REST.model.Tables.ROLE;

@Repository
@Transactional
public class RoleDAOJOOQImpl implements RoleRepositoryJOOQ {
    private final DSLContext create;

    @Autowired
    public RoleDAOJOOQImpl(DSLContext create) {
        this.create = create;
    }

    @Override
    public List<Role> findAllRoles() {
        return create.selectFrom(ROLE)
                .fetchInto(Role.class);
    }


    @Override
    public void addRole(Role role) {
        create.insertInto(ROLE)
                .set(ROLE.ROLE_, role.getRole())
                .execute();
    }

    @Override
    public Role findById(int id) {
        return create.selectFrom(ROLE)
                .where(ROLE.ID.eq((long)id))
                .fetchAny()
                .into(Role.class);
    }
    @Override
    public List<Role> findAll(Condition condition) {
        return create.selectFrom(ROLE)
                .where(condition)
                .fetch()
                .into(Role.class);
    }
    @Override
    public Set<Role> findAllById(List<Integer> roles) {
        return new HashSet<>(create
                .selectFrom(ROLE)
                .where(ROLE.ID.in(roles))
                .fetchInto(Role.class));
    }
}
