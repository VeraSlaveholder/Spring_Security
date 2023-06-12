package com.example.Security_REST.mapper;

import com.example.Security_REST.model.User;
import com.example.Security_REST.model.tables.records.RoleRecord;
import com.example.Security_REST.model.tables.records.UsersRecord;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Component;

import static com.example.Security_REST.model.Tables.ROLE;
import static com.example.Security_REST.model.Tables.USERS;

@Component
public class UserRecordMapper implements RecordMapper<UsersRecord, User> {

    public User map(UsersRecord record, RoleRecord roleRecord) {
        User user = new User();
        user.setUserId(Math.toIntExact(record.get(USERS.USER_ID)));
        user.setName(record.get(USERS.NAME));
        user.setSurname(record.get(USERS.SURNAME));
        user.setAge(record.get(USERS.AGE));
        user.setEmail(record.get(USERS.EMAIL));
        user.setPassword(record.get(USERS.PASSWORD));
        user.setRoles(roleRecord.get(ROLE.ROLE_, new RolesConverter()));
        return user;
    }

    @Override
    public User map(UsersRecord record) {
        User user = new User();
        user.setUserId(Math.toIntExact(record.get(USERS.USER_ID)));
        user.setName(record.get(USERS.NAME));
        user.setSurname(record.get(USERS.SURNAME));
        user.setAge(record.get(USERS.AGE));
        user.setEmail(record.get(USERS.EMAIL));
        user.setPassword(record.get(USERS.PASSWORD));
        return user;
    }
}
