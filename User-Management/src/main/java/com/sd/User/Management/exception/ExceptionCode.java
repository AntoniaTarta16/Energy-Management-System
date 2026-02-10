package com.sd.User.Management.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
    ERR001_USER_NOT_FOUND("User with name %s not found"),
    ERR099_INVALID_CREDENTIALS("Invalid credentials.");

    private final String message;
}
