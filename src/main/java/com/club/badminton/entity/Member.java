package com.club.badminton.entity;

import com.club.badminton.dto.member.MemberUpdateForm;
import com.club.badminton.entity.address.Address;
import com.club.badminton.entity.base.BaseTimeEntity;
import com.club.badminton.entity.club.ClubMember;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "email", "password", "name", "phone", "birthday", "address"})
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    //TODO 비밀번호 암호화
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String phone;

    private LocalDate birthday;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ClubMember> clubMembers = new ArrayList<>();

    public Member(String email, String password, String name, String phone, LocalDate birthday, Address address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
    }

    public void update(MemberUpdateForm form) {
        this.name = form.getName();
        this.phone = form.getPhone();
        this.birthday = form.getBirthday();
        this.address.update(form.getSiDo(), form.getGuGun(), form.getDongRi());
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
