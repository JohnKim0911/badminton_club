package com.club.badminton.init;

import com.club.badminton.init.service.InitAddressService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class InitAddress {

    private final InitAddressService initAddressService;

    @PostConstruct
    public void init() throws IOException {
        initAddressService.createAddresses(); //전국 행정구역 data 생성
    }

}
