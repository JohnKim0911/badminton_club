package com.club.badminton.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    private String siDo; //시, 도
    private String guGun; //구, 군
    private String street; //로 (도로명)

    protected Address() {
    }

    public Address(String siDo, String guGun, String street) {
        this.siDo = siDo;
        this.guGun = guGun;
        this.street = street;
    }
}
