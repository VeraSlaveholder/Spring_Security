package com.example.Security_REST.Exception;

import org.springframework.dao.DataIntegrityViolationException;

public class UserUsernameExistException extends DataIntegrityViolationException {
    public UserUsernameExistException(String msg) {
        super(msg);
    }
    public UserUsernameExistException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
