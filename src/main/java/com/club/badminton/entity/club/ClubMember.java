package com.club.badminton.entity.club;

import com.club.badminton.entity.Member;
import com.club.badminton.entity.budget.Transaction;
import com.club.badminton.entity.base.BaseTimeEntity;
import com.club.badminton.entity.post.Comment;
import com.club.badminton.entity.post.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @Setter
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private ClubMemberStatus status;

    @OneToMany(mappedBy = "writer")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "clubMember")
    private List<Transaction> transactions = new ArrayList<>();

    //연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getClubMembers().add(this);
    }


}
