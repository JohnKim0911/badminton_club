package com.club.badminton.entity.address;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Address {
    //TODO 주소 어떻게 관리하는게 좋을지?
/*
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "si_do_id")
    private SiDo siDo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gu_gun_id")
    private GuGun guGun;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dong_ri_id")
    private DongRi dongRi;
*/

    private String siDo;
    private String guGun;
    private String dongRi;

    public void update(String siDo, String guGun, String dongRi) {
        this.siDo = siDo;
        this.guGun = guGun;
        this.dongRi = dongRi;
    }
}
