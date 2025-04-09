package com.club.badminton.dto.member;


import com.club.badminton.entity.member.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginMember {

    private Long id;
    private String email;
    private String name;
    private String profileImg;

    public LoginMember(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        if (member.getProfileImg() != null) {
            this.profileImg = member.getProfileImg().getStoredName();
        }
    }
}
