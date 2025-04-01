package com.club.badminton.dto.member;


import com.club.badminton.entity.member.Member;
import com.club.badminton.entity.address.Address;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberDto {

    private Long id;
    private String email;
    private String name;
    private String phone;
    private LocalDate birthday;
    private Address address;

    public MemberDto(Member member) {
        id = member.getId();
        email = member.getEmail();
        name = member.getName();
        phone = member.getPhone();
        birthday = member.getBirthday();
        address = member.getAddress();
    }

}
