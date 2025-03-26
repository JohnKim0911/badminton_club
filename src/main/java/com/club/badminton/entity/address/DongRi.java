package com.club.badminton.entity.address;

import jakarta.persistence.*;
import lombok.Getter;

//@Entity
@Getter
public class DongRi { //동, 리

    @Id
    @GeneratedValue
    @Column(name = "dong_ri_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gu_gun_id")
    private GuGun guGun;

    private String name;
}
