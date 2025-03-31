package com.club.badminton.service;

import com.club.badminton.dto.member.*;
import com.club.badminton.entity.Member;
import com.club.badminton.exception.validation.InvalidMemberIdException;
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
    public void signUp(MemberSignUpForm form) {
        validateDuplicatedEmail(form.getEmail());
        validateDuplicatedPhone(form.getPhone());

        Member member = form.toMember();
        memberRepository.save(member);
    }

    private void validateDuplicatedEmail(String email) {
        Optional<Member> byEmail = memberRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            throw new DuplicatedEmailException();
        }
    }

    private void validateDuplicatedPhone(String phone) {
        Optional<Member> byPhone = memberRepository.findByPhone(phone);
        if (byPhone.isPresent()) {
            throw new DuplicatedPhoneException();
        }
    }

    public LoginMember login(LoginForm loginForm) {
        Member member = validateLogin(loginForm);
        return new LoginMember(member);
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
        return toMemberDtos(members);
    }

    private static List<MemberDto> toMemberDtos(List<Member> members) {
        return members.stream()
                .map(m -> new MemberDto(m))
                .collect(Collectors.toList());
    }

    public MemberUpdateForm updateForm(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return MemberUpdateForm.toUpdateForm(member);
        } else {
            throw new InvalidMemberIdException();
        }
    }

    @Transactional
    public void update(MemberUpdateForm form) {
        validateDuplicatedPhone(form.getPhone());

        Optional<Member> optionalMember = memberRepository.findById(form.getId());
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.update(form);
        } else {
            throw new InvalidMemberIdException();
        }

    }
}
