package com.club.badminton.init;

import com.club.badminton.init.service.InitAddressService;
import com.club.badminton.service.AddressService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 전국 행정구역 data 생성
 */
@Component
@RequiredArgsConstructor
public class InitAddress {

    private final InitAddressService initAddressService;
    private final AddressService addressService;

    @PostConstruct
    private void init() throws IOException {
        initAddressService.createAddresses(); // loads from JSON into DB
        addressService.loadCache();           // loads from DB into memory
    }

}
