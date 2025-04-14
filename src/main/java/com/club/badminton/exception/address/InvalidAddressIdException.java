package com.club.badminton.exception.address;

public class InvalidAddressIdException extends RuntimeException {

    public InvalidAddressIdException() {
        super("유효하지 않는 address_id 입니다.");
    }
}
