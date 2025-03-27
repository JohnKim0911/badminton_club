package com.club.badminton.dto;


import com.club.badminton.entity.Member;
import com.club.badminton.entity.address.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private Long id;
    private String email;
    private String password;
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
