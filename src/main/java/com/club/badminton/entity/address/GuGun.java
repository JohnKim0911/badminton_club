package com.club.badminton.entity.address;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

//@Entity
@Getter
public class GuGun { //구, 군

    @Id
    @GeneratedValue
    @Column(name = "gu_gun_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "si_do_id")
    private SiDo siDo;

    private String name;

    @OneToMany(mappedBy = "guGun")
    private List<DongRi> dongRis = new ArrayList<>();

}
