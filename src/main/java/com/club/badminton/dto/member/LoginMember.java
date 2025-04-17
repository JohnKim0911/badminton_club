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

    public static LoginMember of(Member m) {
        LoginMember dto = new LoginMember();

        dto.setId(m.getId());
        dto.setEmail(m.getEmail());
        dto.setName(m.getName());

        if (m.getProfileImg() != null) {
            dto.setProfileImg(m.getProfileImg().getStoredName());
        }

        return dto;
    }

}
