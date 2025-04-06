package com.club.badminton.repository;

import com.club.badminton.entity.member.Member;
import com.club.badminton.entity.address.Address;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/*
@SpringBootTest
@Transactional
//@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void findByName() {
        //given
        LocalDate birthday = LocalDate.of(1990, 5, 23);
        Address address = new Address("서울", "서초구", "서초동");
        Member member = new Member("test@email.com", "1234", "홍길동", "010-1234-5678", birthday, address);
        memberRepository.save(member);

        //when
        Member foundMember = memberRepository.findByEmail(member.getEmail()).get();
        List<Member> allMembers = memberRepository.findAll();

        //then
        assertThat(foundMember).isEqualTo(member);
        assertThat(allMembers).containsExactly(member);
        System.out.println("foundMember = " + foundMember);
    }
}*/
