package com.club.badminton.entity.attachment;

import com.club.badminton.entity.base.BaseEntity;
import com.club.badminton.entity.member.Member;
import com.club.badminton.entity.post.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attachment extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "attachment_id")
    private Long id;

    private String originalName;
    private String storedName;

    //TODO 빼도 되나?
    @OneToOne(mappedBy = "profileImg", fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Attachment(String originalName, String storedName) {
        this.originalName = originalName;
        this.storedName = storedName;
    }

    public void changeMember(Member member) {
        this.member = member;
    }
}
