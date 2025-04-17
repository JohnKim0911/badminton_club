package com.club.badminton.init.service;

import com.club.badminton.entity.address.Address;
import com.club.badminton.entity.member.Member;
import com.club.badminton.entity.member.MemberRole;
import com.club.badminton.repository.AddressRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Random;

@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InitMemberService {

    private final EntityManager em;
    private final AddressRepository addressRepository;

    private long addressCount;

    private void getAddressCount() {
        addressCount = addressRepository.count();
        log.info("InitMemberService.getAddressCount: {}", addressCount);
    }

    public void insertAdmin() {
        getAddressCount();

        String mail = "admin@email.com";
        String password = "password0";
        String name = "admin";
        String phone = createRandomPhone();
        LocalDate birthday = createRandomBirthday();
        Address address = getRandomAddress(addressCount);

        Member admin = new Member(mail, password, name, phone, birthday, address, "xxx로 어딘가", MemberRole.ADMIN);
        em.persist(admin);
    }

    public void insertMembers(int memberCount) {
        for (int i = 1; i <= memberCount; i++) {
            String mail = "user" + i + "@email.com";
            String password = "password" + i;
            String name = "user" + i;
            String phone = createRandomPhone();
            LocalDate birthday = createRandomBirthday();
            Address address = getRandomAddress(addressCount);

            Member member = new Member(mail, password, name, phone, birthday, address, "xxx로 어딘가");
            em.persist(member);
        }
    }

    private String createRandomPhone() {
        Random random = new Random();
        int middle = 1000 + random.nextInt(9000); // 1000~9999
        int last = 1000 + random.nextInt(9000);   // 1000~9999
        return String.format("010-%04d-%04d", middle, last); // 010-1111-2222
    }

    private LocalDate createRandomBirthday() {
        Random random = new Random();
        int randomYear = 1955 + random.nextInt(50); // 1955~2004년
        int randomMonth = 1 + random.nextInt(11); // 1~12월
        int randomDay = 1 + random.nextInt(28); // 1~28일
        return LocalDate.of(randomYear, randomMonth, randomDay);
    }

    private Address getRandomAddress(long addressCount) {
        Random random = new Random();
        long randomAddressId = 1 + random.nextLong(addressCount); //address IDs start from 1
        return addressRepository.findById(randomAddressId).get();
    }
}