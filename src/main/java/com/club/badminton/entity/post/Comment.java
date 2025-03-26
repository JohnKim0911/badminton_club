package com.club.badminton.entity.post;

import com.club.badminton.entity.base.BaseTimeEntity;
import com.club.badminton.entity.club.ClubMember;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_member_id")
    private ClubMember writer;

    @Column
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> childComments = new ArrayList<>();

    private void setParent(Comment parent) {
        this.parent = parent;
    }

    public void addChildComment(Comment child) {
        childComments.add(child);
        child.setParent(this);
    }
}
