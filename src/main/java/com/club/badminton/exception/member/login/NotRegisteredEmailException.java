package com.club.badminton.exception.member.login;

public class NotRegisteredEmailException extends RuntimeException {

    public NotRegisteredEmailException() {
        super("등록되지 않은 이메일입니다.");
    }

}
