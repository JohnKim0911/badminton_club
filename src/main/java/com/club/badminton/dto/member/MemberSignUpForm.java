package com.club.badminton.dto.member;

import com.club.badminton.entity.member.Member;
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
public class MemberSignUpForm {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 20, message = "비밀번호는 8 ~ 20자이어야 합니다.")
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    private LocalDate birthday;

    @NotBlank
    private String siDo;

    @NotBlank
    private String guGun;

    @NotBlank
    private String dongRi;

    @AssertTrue
    private Boolean hasAcceptedTerms;

    public Member toMember() {
        return new Member(email, password, name, phone, birthday, new Address(siDo, guGun, dongRi));
    }
}
