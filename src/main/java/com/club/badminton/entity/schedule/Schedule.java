package com.club.badminton.entity.schedule;

import com.club.badminton.entity.base.BaseEntity;
import com.club.badminton.entity.club.Club;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    private LocalDateTime time;
    private String title;
    private String contents;

}
