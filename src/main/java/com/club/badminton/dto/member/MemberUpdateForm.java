package com.club.badminton.dto.member;

import com.club.badminton.entity.Member;
import com.club.badminton.entity.address.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class MemberUpdateForm {

    private Long id;
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotBlank
    private String siDo;

    @NotBlank
    private String guGun;

    @NotBlank
    private String dongRi;

    public static MemberUpdateForm toUpdateForm(Member m) {
        MemberUpdateForm form = new MemberUpdateForm();
        form.setId(m.getId());
        form.setEmail(m.getEmail());
        form.setName(m.getName());
        form.setPhone(m.getPhone());
        form.setBirthday(m.getBirthday());

        Address address = m.getAddress();
        form.setSiDo(address.getSiDo());
        form.setGuGun(address.getGuGun());
        form.setDongRi(address.getDongRi());
        return form;
    }
}
