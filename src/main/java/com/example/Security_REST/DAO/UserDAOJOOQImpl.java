package com.example.Security_REST.DAO;

import com.example.Security_REST.mapper.UserRecordMapper;
import com.example.Security_REST.model.Role;
import com.example.Security_REST.model.User;
import com.example.Security_REST.model.tables.records.RoleRecord;
import com.example.Security_REST.model.tables.records.UsersRecord;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.ResultQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.example.Security_REST.model.Tables.*;

@Repository
@Transactional
public class UserDAOJOOQImpl implements UserRepositoryJOOQ {
    private final DSLContext create;

    private final UserRecordMapper recordMapper;

    public UserDAOJOOQImpl(DSLContext create, UserRecordMapper recordMapper) {
        this.create = create;
        this.recordMapper = recordMapper;
    }

    @Override
    public List<User> findAll() {
        return create.select(USERS.USER_ID, USERS.NAME, USERS.SURNAME, USERS.AGE, USERS.EMAIL, USERS.PASSWORD, ROLE.ROLE_)
                .from(USERS)
                .join(USERS_ROLES).on(USERS.USER_ID.eq(USERS_ROLES.USER_ID))
                .join(ROLE).on(USERS_ROLES.ROLE_ID.eq(ROLE.ID))
                .orderBy(USERS.USER_ID)
                .fetch()
                .map(r -> recordMapper.map(r.into(UsersRecord.class), r.into(RoleRecord.class)));
    }

    @Override
    public User getById(int id) {
        return create.select(USERS.USER_ID, USERS.NAME, USERS.SURNAME, USERS.AGE, USERS.EMAIL, USERS.PASSWORD, ROLE.ROLE_)
                .from(USERS)
                .join(USERS_ROLES).on(USERS.USER_ID.eq(USERS_ROLES.USER_ID))
                .join(ROLE).on(USERS_ROLES.ROLE_ID.eq(ROLE.ID))
                .where(USERS.USER_ID.eq((long) id))
                .fetchOne()
                .map(r -> recordMapper.map(r.into(UsersRecord.class), r.into(RoleRecord.class)));
    }

    @Override
    public void save(User user) {
        create.insertInto(USERS, USERS.NAME, USERS.SURNAME, USERS.AGE, USERS.EMAIL, USERS.PASSWORD)
                .values(user.getName(), user.getSurname(), user.getAge(), user.getEmail(), user.getPassword())
                .execute();

        // Получение последний сгенерированный идентификатор пользователя
        ResultQuery<Record1<Long>> lastIdQuery = create.select(USERS.USER_ID)
                .from(USERS)
                .where(USERS.NAME.eq(user.getName()))
                .orderBy(USERS.USER_ID.desc())
                .limit(1);

        Long userId = lastIdQuery.fetchOne().value1();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            long roleId;
            roleId = role.getId();
            create.insertInto(USERS_ROLES, USERS_ROLES.USER_ID, USERS_ROLES.ROLE_ID)
                    .values(userId, roleId) // Здесь roleId - идентификатор роли
                    .execute();
        }
    }

    @Override
    public void deleteById(int id) {
        create.deleteFrom(USERS)
                .where(USERS.USER_ID.eq((long) id))
                .execute();
        create.deleteFrom(USERS_ROLES)
                .where(USERS_ROLES.USER_ID.eq((long) id))
                .execute();
    }

    @Override
    public User findByUsername(String name) {
        return create.select(USERS.USER_ID, USERS.NAME, USERS.SURNAME, USERS.AGE, USERS.EMAIL, USERS.PASSWORD, ROLE.ROLE_)
                .from(USERS)
                .join(USERS_ROLES).on(USERS.USER_ID.eq(USERS_ROLES.USER_ID))
                .join(ROLE).on(USERS_ROLES.ROLE_ID.eq(ROLE.ID))
                .where(USERS.NAME.eq(name))
                .fetchOne()
                .map(r -> recordMapper.map(r.into(UsersRecord.class), r.into(RoleRecord.class)));
    }

    @Override
    public void update(User user) {
        create.update(USERS)
                .set(USERS.NAME, user.getName())
                .set(USERS.SURNAME, user.getSurname())
                .set(USERS.AGE, user.getAge())
                .set(USERS.EMAIL, user.getEmail())
                .set(USERS.PASSWORD, user.getPassword())
                .where(USERS.USER_ID.eq((long) user.getUserId()))
                .execute();

        // Удаление текущих ролей пользователя
        create.deleteFrom(USERS_ROLES)
                .where(USERS_ROLES.USER_ID.eq((long) user.getUserId()))
                .execute();

        // Добавление новых ролей пользователя
        Set<Role> roles = user.getRoles();
        if (roles != null) {
            for (Role role : roles) {
                create.insertInto(USERS_ROLES, USERS_ROLES.USER_ID, USERS_ROLES.ROLE_ID)
                        .values((long) user.getUserId(), (long) role.getId())
                        .execute();
            }
        }
    }
}
