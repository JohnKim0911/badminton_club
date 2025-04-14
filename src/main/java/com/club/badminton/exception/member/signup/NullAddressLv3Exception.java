package com.club.badminton.exception.member.signup;

public class NullAddressLv3Exception extends RuntimeException {

    public NullAddressLv3Exception() {
        super("세 번째 지역을 선택해주세요.");
    }

}
