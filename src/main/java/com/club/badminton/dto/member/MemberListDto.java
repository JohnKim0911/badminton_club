package com.club.badminton.dto.member;

import com.club.badminton.entity.address.Address;
import com.club.badminton.entity.member.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class MemberListDto {

    private Long id;
    private String email;
    private String name;
    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String addressLv1;
    private String addressLv2;
    private String addressLv3;

    // TODO 생성일, 마지막 로그인 시간 추가?

    public static MemberListDto of(Member m) {
        MemberListDto dto = new MemberListDto();

        dto.setId(m.getId());
        dto.setEmail(m.getEmail());
        dto.setName(m.getName());
        dto.setPhone(m.getPhone());

        dto.setBirthday(m.getBirthday());

        Address a = m.getAddress();
        dto.setAddressLv1(a.getLv1().getName());
        dto.setAddressLv2(a.getLv2().getName());
        if (a.getLv3() != null) {
            dto.setAddressLv3(a.getLv3().getName());
        }

        return dto;
    }

}
