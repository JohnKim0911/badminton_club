package com.club.badminton.web;

import com.club.badminton.entity.Member;
import com.club.badminton.entity.address.Address;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberForm {

    @NotBlank
    @Email(message = "이메일 양식을 지켜주세요.")
    private String email;

    @NotBlank
    @Size(min = 8, max = 20, message = "비밀번호는 8 ~ 20자 이여야 합니다.")
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    private LocalDate birthday;

    @NotBlank(message = "주소(시/도)는 필수입니다.")
    private String siDo;

    @NotBlank(message = "주소(구/군)는 필수입니다.")
    private String guGun;

    @NotBlank(message = "주소(동/리)는 필수입니다.")
    private String dongRi;

    @AssertTrue(message = "약관에 동의해야만 회원가입 할 수 있습니다.")
    private Boolean hasAcceptedTerms;

    public static Member toMember(MemberForm form) {
        Address address = new Address(form.getSiDo(), form.getGuGun(), form.getDongRi());
        return new Member(form.getEmail(), form.getPassword(), form.getName(), form.getPhone(), form.getBirthday(), address);
    }
}
