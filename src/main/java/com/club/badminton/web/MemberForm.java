package com.club.badminton.web;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberForm {

    @NotEmpty(message = "이메일은 필수입니다.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;

    @NotEmpty(message = "이름은 필수입니다.")
    private String name;

    @NotEmpty(message = "연락처는 필수입니다.")
    private String phone;

    private LocalDate birthday;

    private String siDo;
    private String guGun;
    private String dongRi;
}
