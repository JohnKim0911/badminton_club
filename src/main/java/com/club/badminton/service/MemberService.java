package com.club.badminton.service;

import com.club.badminton.dto.member.*;
import com.club.badminton.entity.address.Address;
import com.club.badminton.entity.member.Member;
import com.club.badminton.entity.member.MemberStatus;
import com.club.badminton.exception.InvalidMemberIdException;
import com.club.badminton.exception.validation.login.ResignedMemberException;
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
    private final AddressService addressService;

    @Transactional
    public void signUp(MemberSignUpForm form) {
        validateSignUp(form);
        Address address = addressService.findById(form.getAddressId());
        Member member = form.toMember(address);
        memberRepository.save(member);
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

        if (member.getStatus() == MemberStatus.RESIGNED) {
            throw new ResignedMemberException();
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
        Member member = findMemberById(memberId);
        return MemberUpdateForm.toUpdateForm(member);
    }

    private Member findMemberById(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isEmpty()) {
            throw new InvalidMemberIdException();
        }
        return optionalMember.get();
    }

    @Transactional
    public void update(MemberUpdateForm form) {
        validateUpdate(form);

        Member member = findMemberById(form.getId());
        Address address = addressService.findById(form.getAddressId());
        member.update(form, address);
    }

    private void validateUpdate(MemberUpdateForm form) {
        Optional<Member> byPhone = memberRepository.findByPhone(form.getPhone());
        if (byPhone.isPresent() && !byPhone.get().getId().equals(form.getId())) {
            throw new DuplicatedPhoneException();
        }
    }

    public boolean checkPassword(Long memberId, String inputPassword) {
        Member member = findMemberById(memberId);
        return inputPassword.equals(member.getPassword());
    }

    @Transactional
    public void updatePassword(Long memberId, String newPassword) {
        Member member = findMemberById(memberId);
        member.changePassword(newPassword);
    }

    @Transactional
    public void delete(Long memberId) {
        Member member = findMemberById(memberId);
        member.changeStatus(MemberStatus.RESIGNED);
    }
}
