package com.club.badminton.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
//TODO 연관관계 메서드 추가하기
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    private String name;
    private String phone;
    private LocalDate birthday;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ClubMember> clubMembers = new ArrayList<>();

}
