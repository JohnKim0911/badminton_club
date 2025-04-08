package com.club.badminton.dto.member;

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
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotNull
    private Long addressId;

    public static MemberUpdateForm toUpdateForm(Member m) {
        MemberUpdateForm form = new MemberUpdateForm();
        form.setId(m.getId());
        form.setEmail(m.getEmail());
        form.setName(m.getName());
        form.setPhone(m.getPhone());
        form.setBirthday(m.getBirthday());
        form.setAddressId(m.getAddress().getId());
        return form;
    }
}
