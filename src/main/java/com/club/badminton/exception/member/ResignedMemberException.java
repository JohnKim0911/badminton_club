package com.club.badminton.exception.member;

public class ResignedMemberException extends RuntimeException {

    public ResignedMemberException() {
        super("회원탈퇴 계정입니다.");
    }
}
