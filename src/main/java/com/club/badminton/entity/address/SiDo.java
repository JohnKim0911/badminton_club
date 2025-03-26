package com.club.badminton.entity.address;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SiDo { //시, 도

    @Id
    @GeneratedValue
    @Column(name = "si_do_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "siDo")
    private List<GuGun> guGuns = new ArrayList<>();


}
