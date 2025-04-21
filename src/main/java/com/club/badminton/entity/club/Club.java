package com.club.badminton.entity.club;

import com.club.badminton.dto.club.CreateClubForm;
import com.club.badminton.entity.address.Address;
import com.club.badminton.entity.attachment.Attachment;
import com.club.badminton.entity.schedule.Schedule;
import com.club.badminton.entity.base.BaseEntity;
import com.club.badminton.entity.budget.Budget;
import com.club.badminton.entity.post.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "club_id")
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    private String detailAddress;

    //TODO 클럽 소개는 다른 엔티티로 빼기? 클럽 사진도 추가
    private String description; //간단한 소개

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_id")
    private Attachment clubImg;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id")
    private Budget budget;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<ClubMember> clubMembers = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Schedule> schedules = new ArrayList<>();

    private Club(String name, Address address, String detailAddress) {
        this.name = name;
        this.address = address;
        this.detailAddress = detailAddress;
    }

    public static Club of(CreateClubForm form, Address address) {
        return new Club(form.getName(), address, form.getDetailAddress());
    }
}
