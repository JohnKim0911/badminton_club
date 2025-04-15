package com.club.badminton;

import com.club.badminton.dto.address.AddressHierarchyDto;
import com.club.badminton.entity.address.Address;
import com.club.badminton.entity.address.AddressLv1;
import com.club.badminton.entity.address.AddressLv2;
import com.club.badminton.entity.address.AddressLv3;
import com.club.badminton.entity.member.Member;
import com.club.badminton.repository.AddressRepository;
import com.club.badminton.service.AddressService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
        initMemberService.insertMembers(50);

        ADDRESSES = addressService.getFullAddressHierarchy();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitAddressService {

        private final EntityManager em;

        public Map<String, Object> readJson() {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
            try (InputStream jsonInputStream = new ClassPathResource("addresses.json").getInputStream()) {
                return mapper.readValue(jsonInputStream, typeRef);
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to read address JSON", e);
            }
        }

        public void insertAllAddresses(Map<String, Object> addressMap) {
            for (Map.Entry<String, Object> lv1Entry : addressMap.entrySet()) {
                String lv1Name = lv1Entry.getKey();
                Object lv2Obj = lv1Entry.getValue();

                AddressLv1 lv1 = new AddressLv1(lv1Name);
                em.persist(lv1);  // Persist parent FIRST so it’s managed

                if (lv2Obj instanceof List) {
                    List<String> lv2Names = (List<String>) lv2Obj;
                    if (lv2Names.isEmpty()) {
                        // e.g., "경기도": { "의정부시": [] } (if you're using 2-level JSON)
                        // probably not used in this block actually
                    } else {
                        for (String lv2Name : lv2Names) {
                            AddressLv2 lv2 = new AddressLv2(lv2Name);
                            lv1.addChild(lv2);
                            em.persist(lv2); // optional (if cascade = ALL on lv1→lv2)

                            Address address = new Address(lv1, lv2, null);
                            em.persist(address);
                            log.info("Inserting Address: {}, {}", lv1Name, lv2.getName());
                        }
                    }
                } else if (lv2Obj instanceof Map) {
                    Map<String, List<String>> lv2Map = (Map<String, List<String>>) lv2Obj;
                    for (Map.Entry<String, List<String>> lv2Entry : lv2Map.entrySet()) {
                        String lv2Name = lv2Entry.getKey();
                        List<String> lv3Names = lv2Entry.getValue();

                        AddressLv2 lv2 = new AddressLv2(lv2Name);
                        lv1.addChild(lv2);

                        if (lv3Names.isEmpty()) {
                            Address address = new Address(lv1, lv2, null);
                            em.persist(address);
                            log.info("Inserting Address: {}, {}", lv1Name, lv2.getName());
                        } else {
                            for (String lv3Name : lv3Names) {
                                AddressLv3 lv3 = new AddressLv3(lv3Name);
                                lv2.addChild(lv3);

                                Address address = new Address(lv1, lv2, lv3);
                                em.persist(address);
                                log.info("Inserting Address: {}, {}, {}", lv1Name, lv2.getName(), lv3Name);
                            }
                        }
                    }
                }
            }
        }
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitMemberService {

        private final EntityManager em;
        private final AddressRepository addressRepository;

        public void insertMembers(int memberCount) {
            long addressSize = addressRepository.count();

            for (int i = 1; i <= memberCount; i++) {
                String mail = "user" + i + "@email.com";
                String password = "password" + i;
                String name = "user" + i;
                String phone = createRandomPhone();
                LocalDate birthday = createRandomBirthday();
                Address address = getRandomAddress(addressSize);

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

        private Address getRandomAddress(long addressSize) {
            Random random = new Random();
            long randomAddressId = 1 + random.nextLong(addressSize); //address IDs start from 1
            return addressRepository.findById(randomAddressId).get();
        }
    }

}
