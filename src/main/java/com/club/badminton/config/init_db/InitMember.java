package com.club.badminton.config.init_db;

import com.club.badminton.config.init_db.service.InitMemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class InitMember {

    private final InitMemberService initMemberService;

    @PostConstruct
    private void init() {
        initMemberService.createMembers(50); //50명 랜덤 데이터 추가
    }

}
