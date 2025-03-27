package com.club.badminton.service;

import com.club.badminton.dto.MemberDto;
import com.club.badminton.entity.Member;
import com.club.badminton.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateMember(Member member) {
        Optional<Member> foundMember = memberRepository.findByEmail(member.getEmail());
        if (foundMember.isPresent()) {
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }
    }

    public List<MemberDto> findMembers() {
        List<Member> members = memberRepository.findAll();

        //엔티티 -> DTO 변환
        List<MemberDto> memberDtos = members.stream()
                .map(m -> new MemberDto(m))
                .collect(Collectors.toList());

        return memberDtos;
    }

    public MemberDto login(MemberDto memberDto) {
        Member member = validateLogin(memberDto);
        return new MemberDto(member);
    }

    private Member validateLogin(MemberDto memberDto) {
        Optional<Member> optionalMember = memberRepository.findByEmail(memberDto.getEmail());

        if (optionalMember.isEmpty()) {
            throw new IllegalStateException("등록되지 않은 이메일입니다.");
        }

        Member member = optionalMember.get();
        if (!member.getPassword().equals(memberDto.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        return member;
    }
}
