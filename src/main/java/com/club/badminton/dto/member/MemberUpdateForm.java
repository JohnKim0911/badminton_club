package com.club.badminton.dto.member;

import com.club.badminton.entity.address.Address;
import com.club.badminton.entity.member.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class MemberUpdateForm {

    private Long id;
    private String email; //readOnly

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotNull
    private Long addressLv1;

    @NotNull
    private Long addressLv2;

    private Long addressLv3;
    private String detailAddress;

    //TODO Builder Pattern 적용?
    public static MemberUpdateForm of(Member m) {
        MemberUpdateForm dto = new MemberUpdateForm();

        dto.setId(m.getId());
        dto.setEmail(m.getEmail());
        dto.setName(m.getName());
        dto.setPhone(m.getPhone());
        dto.setBirthday(m.getBirthday());

        Address address = m.getAddress();
        dto.setAddressLv1(address.getLv1().getId());
        dto.setAddressLv2(address.getLv2().getId());
        if (address.getLv3() != null) {
            dto.setAddressLv3(address.getLv3().getId());
        }

        dto.setDetailAddress(m.getDetailAddress());

        return dto;
    }
}
