package com.club.badminton.dto.member;


import com.club.badminton.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginMember {

    private Long id;
    private String email;
    private String name;
    private String profileImage; //TODO 실제 사용할 수 있도록 수정 필요

    public LoginMember(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
    }
}
