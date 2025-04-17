package com.club.badminton.init;

import com.club.badminton.dto.address.AddressHierarchyDto;
import com.club.badminton.init.service.InitAddressService;
import com.club.badminton.init.service.InitMemberService;
import com.club.badminton.service.AddressService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitApp {

    private final InitAddressService initAddressService;
    private final InitMemberService initMemberService;
    private final AddressService addressService;

    public static AddressHierarchyDto ADDRESSES; // 애플리케이션에서 계속 공유할 주소

    @PostConstruct
    public void init() {
        log.info("InitApp.init");

        Map<String, Object> addressMap = initAddressService.readJson();
        log.info("addressMap: {}", addressMap);

        initAddressService.insertAllAddresses(addressMap);

        initMemberService.insertAdmin();
        initMemberService.insertMembers(50);

        ADDRESSES = addressService.getFullAddressHierarchy();
    }





}
