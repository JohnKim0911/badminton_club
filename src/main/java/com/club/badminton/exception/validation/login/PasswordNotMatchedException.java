package com.club.badminton.exception.validation.login;

public class PasswordNotMatchedException extends RuntimeException {

    public PasswordNotMatchedException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
