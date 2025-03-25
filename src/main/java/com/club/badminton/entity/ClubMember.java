package com.club.badminton.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "club_member")
@Getter
public class ClubMember extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "club_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private ClubMemberStatus status;

    //연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getClubMembers().add(this);
    }

}
