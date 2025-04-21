package com.club.badminton.service;

import com.club.badminton.dto.member.LoginMember;
import com.club.badminton.entity.member.LoginHistory;
import com.club.badminton.entity.member.Member;
import com.club.badminton.repository.LoginHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginHistoryService {

    private final LoginHistoryRepository loginHistoryRepository;

    @Transactional
    public void login(Member member) {
        loginHistoryRepository.save(LoginHistory.of(member));
    }

    @Transactional
    public void logout(LoginMember loginMember) {
        LoginHistory loginHistory = loginHistoryRepository.findFirstByMemberIdOrderByLoginTimeDesc(loginMember.getId()).get();
        loginHistory.updateLogoutTime();
    }
}
