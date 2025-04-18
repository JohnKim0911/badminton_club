package com.club.badminton.exception.member;

public class AdminCannotBeDeletedException extends RuntimeException {

    public AdminCannotBeDeletedException() {
        super("어드민 계정은 삭제 할 수 없습니다.");
    }
}
