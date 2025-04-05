package com.club.badminton.init;

import com.club.badminton.init.service.InitMemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class InitMember {

    private final InitMemberService initMemberService;

    @PostConstruct
    public void init() {
        initMemberService.createMembers(50); //50명 랜덤 데이터 추가
    }

}
