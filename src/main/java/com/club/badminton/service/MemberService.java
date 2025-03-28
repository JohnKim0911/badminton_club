package com.club.badminton.service;

import com.club.badminton.dto.LoginForm;
import com.club.badminton.dto.MemberDto;
import com.club.badminton.dto.MemberSignUpForm;
import com.club.badminton.entity.Member;
import com.club.badminton.exception.validation.signup.DuplicatedEmailException;
import com.club.badminton.exception.validation.signup.DuplicatedPhoneException;
import com.club.badminton.exception.validation.login.NotRegisteredEmailException;
import com.club.badminton.exception.validation.login.PasswordNotMatchedException;
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
    public Long signUp(MemberSignUpForm form) {
        validateSignUp(form);
        Member member = form.toMember();
        memberRepository.save(member);
        return member.getId();
    }

    private void validateSignUp(MemberSignUpForm form) {
        Optional<Member> byEmail = memberRepository.findByEmail(form.getEmail());
        if (byEmail.isPresent()) {
            throw new DuplicatedEmailException();
        }
        Optional<Member> byPhone = memberRepository.findByPhone(form.getPhone());
        if (byPhone.isPresent()) {
            throw new DuplicatedPhoneException();
        }
    }

    public MemberDto login(LoginForm loginForm) {
        Member member = validateLogin(loginForm);
        return new MemberDto(member);
    }

    private Member validateLogin(LoginForm loginForm) {
        Optional<Member> optionalMember = memberRepository.findByEmail(loginForm.getEmail());

        if (optionalMember.isEmpty()) {
            throw new NotRegisteredEmailException();
        }

        Member member = optionalMember.get();
        if (!member.getPassword().equals(loginForm.getPassword())) {
            throw new PasswordNotMatchedException();
        }
        return member;
    }

    public List<MemberDto> findMembers() {
        List<Member> members = memberRepository.findAll();
        return getMemberDtos(members);
    }

    private static List<MemberDto> getMemberDtos(List<Member> members) {
        List<MemberDto> memberDtos = members.stream()
                .map(m -> new MemberDto(m))
                .collect(Collectors.toList());
        return memberDtos;
    }
}
