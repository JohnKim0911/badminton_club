package com.club.badminton.entity.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginHistory {

    @Id
    @GeneratedValue
    @Column(name = "login_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private LocalDateTime loginTime;

    private LocalDateTime logoutTime;

    private LoginHistory(Member member) {
        this.member = member;
        this.loginTime = LocalDateTime.now();
    }

    public static LoginHistory of(Member member) {
        return new LoginHistory(member);
    }

    public void updateLogoutTime() {
        this.logoutTime = LocalDateTime.now();
    }
}
