package com.club.badminton.service;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class MemberServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberService memberService;

/*    @Test
    @DisplayName("동일한 이메일로 가입시 예외발생")
    public void validateDuplicatedMember() {
        //given
        LocalDate birthday = LocalDate.of(1990, 5, 23);
        Address address = new Address("서울", "서초구", "서초동");
        Member member1 = new Member("test@email.com", "1234", "홍길동", "010-1234-5678", birthday, address);
        memberService.join(member1);

        //when
        Member member2 = new Member("test@email.com", "1234", "김길동", "011-1111-1111", birthday, address);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 사용중인 이메일입니다.");
    }*/

}