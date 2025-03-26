package com.club.badminton.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_member_id")
    private ClubMember writer;

    private String title;

    @Column(length = 500)
    private String contents;

    @Enumerated(EnumType.STRING)
    private AttachmentStatus attachmentStatus;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    private int viewCount;
}
