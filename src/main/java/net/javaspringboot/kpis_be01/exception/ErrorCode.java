package net.javaspringboot.kpis_be01.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    USER_EXISTED(1002,"User existed"),
    INVALID_KEY(1001,"invalid message key"),
    FULLNAME_INVALID(1003,"Fullname must be atleast 3 characters"),
    PASSWORD_INVALID(1004,"Password must be atleast 8 characters"),
    USER_NOT_EXISTED(1005,"User not existed"),
    UNAUTHENTICATED(100,"Unauthenticated")
    ;
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private  int code;
    private String message;
}
