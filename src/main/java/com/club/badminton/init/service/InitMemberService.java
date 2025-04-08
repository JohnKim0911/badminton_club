package com.club.badminton.init.service;

import com.club.badminton.dto.address.AddressDto;
import com.club.badminton.entity.address.Address;
import com.club.badminton.entity.member.Member;
import com.club.badminton.service.AddressService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Component
@Transactional
@RequiredArgsConstructor
public class InitMemberService {

    private final EntityManager em;
    private final AddressService addressService;

    public void createMembers(int memberCount) {
        List<AddressDto> dtoList = addressService.getMostSpecificDtoList();

        for (int i = 1; i <= memberCount; i++) {
            String mail = "user" + i + "@email.com";
            String password = "password" + i;
            String name = "user" + i;
            String phone = createRandomPhone();
            LocalDate birthday = createRandomBirthday();
            Address address = getRandomAddress(dtoList);

            Member member = new Member(mail, password, name, phone, birthday, address);
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
        int randomYear = random.nextInt(50) + 1955; // 1955~2004년
        int randomMonth = random.nextInt(11) + 1; // 1~12월
        int randomDay = random.nextInt(28) + 1; // 1~28일
        return LocalDate.of(randomYear, randomMonth, randomDay);
    }

    private Address getRandomAddress(List<AddressDto> dtoList) {
        Random random = new Random();
        AddressDto dto = dtoList.get(random.nextInt(dtoList.size()));
        return addressService.findById(dto.getId());
    }

}
