package com.club.badminton.entity.post;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Attachment {

    @Id
    @GeneratedValue
    @Column(name = "attachment_id")
    private Long id;

    private String originalName;
    private String storedName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
