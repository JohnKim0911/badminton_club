package com.club.badminton.service;

import com.club.badminton.entity.Member;
import com.club.badminton.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(Member member) {
        validateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 회원가입 검증
     */
    private void validateMember(Member member) {
        Optional<Member> foundMember = memberRepository.findByEmail(member.getEmail());
        if (foundMember.isPresent()) {
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }
    }

    /**
     * 전체회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 조회
     */
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

}
