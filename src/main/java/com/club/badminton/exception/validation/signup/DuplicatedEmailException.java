package com.club.badminton.exception.validation.signup;

public class DuplicatedEmailException extends RuntimeException {

    public DuplicatedEmailException() {
        super("이미 사용중인 이메일입니다.");
    }
}
