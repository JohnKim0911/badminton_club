package com.club.badminton.dto.member;


import com.club.badminton.entity.address.Address;
import com.club.badminton.entity.member.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class MemberDto {

    private Long id;
    private String profileImg;
    private String email;
    private String name;
    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String addressLv1;
    private String addressLv2;
    private String addressLv3;
    private String detailAddress;

    public static MemberDto toDto(Member m) {
        MemberDto dto = new MemberDto();

        if (m.getProfileImg() != null) {
            dto.setProfileImg(m.getProfileImg().getStoredName());
        }

        dto.setId(m.getId());
        dto.setEmail(m.getEmail());
        dto.setName(m.getName());
        dto.setPhone(m.getPhone());
        dto.setBirthday(m.getBirthday());

        Address address = m.getAddress();
        dto.setAddressLv1(address.getLv1().getName());
        dto.setAddressLv2(address.getLv2().getName());
        if (address.getLv3() != null) {
            dto.setAddressLv3(address.getLv3().getName());
        }

        return dto;
    }

}
