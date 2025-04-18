package com.club.badminton.exception.member;

public class DuplicatedPhoneException extends RuntimeException {

    public DuplicatedPhoneException() {
        super("이미 사용중인 연락처입니다.");
    }
}
