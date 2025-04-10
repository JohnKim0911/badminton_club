package com.club.badminton.exception.member;

public class InvalidMemberIdException extends RuntimeException {
    public InvalidMemberIdException() {
        super("유효하지 않는 회원 id 입니다.");
    }
}
