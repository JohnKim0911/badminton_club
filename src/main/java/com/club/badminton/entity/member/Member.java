package com.club.badminton.entity.member;

import com.club.badminton.dto.member.MemberSignUpForm;
import com.club.badminton.dto.member.MemberUpdateForm;
import com.club.badminton.entity.address.Address;
import com.club.badminton.entity.attachment.Attachment;
import com.club.badminton.entity.base.BaseTimeEntity;
import com.club.badminton.entity.club.ClubMember;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "email", "password", "name", "phone", "birthday", "address", "detailAddress", "status", "profileImg"})
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    //TODO 비밀번호 암호화
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String phone;

    private LocalDate birthday;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    private String detailAddress;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_id")
    private Attachment profileImg;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ClubMember> clubMembers = new ArrayList<>();

    public Member(String email, String password, String name, String phone, LocalDate birthday, Address address, String detailAddress) {
        this(email, password, name, phone, birthday, address, detailAddress, MemberRole.USER);
    }

    public Member(String email, String password, String name, String phone, LocalDate birthday, Address address, String detailAddress, MemberRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.detailAddress = detailAddress;
        this.role = role;
        this.status = MemberStatus.ACTIVE;
    }

    public static Member of(MemberSignUpForm form, Address address) {
        return new Member(
                form.getEmail(),
                form.getPassword(),
                form.getName(),
                form.getPhone(),
                form.getBirthday(),
                address,
                form.getDetailAddress()
        );
    }

    public void changeProfileImg(Attachment attachment) {
        this.profileImg = attachment;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeStatus(MemberStatus status) {
        this.status = status;
    }

    public void update(MemberUpdateForm form, Address address) {
        this.name = form.getName();
        this.phone = form.getPhone();
        this.birthday = form.getBirthday();
        this.address = address;
        this.detailAddress = form.getDetailAddress();
    }
}
