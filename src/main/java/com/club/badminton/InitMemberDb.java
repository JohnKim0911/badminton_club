package com.club.badminton;

import com.club.badminton.entity.Member;
import com.club.badminton.entity.address.Address;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Component
@RequiredArgsConstructor
public class InitMemberDb {

    private final InitMemberService initMemberService;

    @PostConstruct
    public void init() {
        initMemberService.createMembers(50); //50명 랜덤 데이터 추가
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitMemberService {

        private final EntityManager em;

        public void createMembers(int memberCount) {
            for (int i = 1; i <= memberCount; i++) {
                String mail = "user" + i + "@email.com";
                String password = "password" + i;
                String name = "user" + i;

                Member member = new Member(mail, password, name, createRandomPhone(), createRandomBirthday(), createRandomAddress());
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

        private Address createRandomAddress() {
            Map<String, List<String>> siDoMap = new HashMap<>();
            siDoMap.put("서울특별시", Arrays.asList("강남구", "종로구", "중구", "용산구", "성동구"));
            siDoMap.put("경기도", Arrays.asList("성남시", "수원시", "고양시", "용인시"));
            siDoMap.put("부산광역시", Arrays.asList("해운대구", "사하구", "금정구", "연제구"));

            Random random = new Random();

            // 시, 도
            List<String> siDos = new ArrayList<>(siDoMap.keySet());
            String randomSiDo = siDos.get(random.nextInt(siDos.size()));

            // 구, 군
            List<String> guGuns = siDoMap.get(randomSiDo);
            String randomGuGun = guGuns.get(random.nextInt(guGuns.size()));

            //동, 리 - 상위 지역에 상관없이 우선 랜덤으로 넣기
            List<String> dongRiList = Arrays.asList(
                    "서초동", "금곡동", "신림동", "압구정동", "반포동",
                    "대치동", "잠실동", "송파동", "명동", "논현동",
                    "월계동", "상계동", "화양동", "옥수동", "도곡동",
                    "성수동", "연남동", "수유동", "공릉동", "방배동"
            );
            String randomDongRi = dongRiList.get(random.nextInt(dongRiList.size()));

            return new Address(randomSiDo, randomGuGun, randomDongRi);
        }
    }
}
