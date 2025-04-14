package com.club.badminton.dto.member;

import com.club.badminton.entity.address.Address;
import com.club.badminton.entity.member.Member;
import jakarta.validation.constraints.*;
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

    @NotNull
    private Long addressLv1;

    @NotNull
    private Long addressLv2;

    private Long addressLv3;

    private String detailAddress;

    @AssertTrue
    private Boolean hasAcceptedTerms;

}

/*
@NotBlank: only for String, CharSequence.
@NotNull: Anything (including Long)
@NotEmpty: Collections, Strings, Arrays
 */
