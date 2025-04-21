package com.club.badminton.service;

import com.club.badminton.dto.member.*;
import com.club.badminton.entity.address.Address;
import com.club.badminton.entity.attachment.Attachment;
import com.club.badminton.entity.member.Member;
import com.club.badminton.entity.member.MemberRole;
import com.club.badminton.entity.member.MemberStatus;
import com.club.badminton.exception.member.*;
import com.club.badminton.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final AddressService addressService;
    private final AttachmentService attachmentService;
    private final LoginHistoryService loginHistoryService;

    @Transactional
    public void signUp(MemberSignUpForm form) {
        log.info("MemberSignUpForm: {}", form);
        validateSignUp(form);
        Address address = addressService.findByLevelIds(form.getAddressLv1(), form.getAddressLv2(), form.getAddressLv3());
        Member member = Member.of(form, address);
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

        if (form.getAddressLv3() == null && addressService.shouldHaveLv3(form.getAddressLv2()) ) {
            throw new NullAddressLv3Exception();
        }
    }

    @Transactional
    public LoginMember login(LoginForm loginForm) {
        Member member = validateLogin(loginForm);
        loginHistoryService.login(member);
        return LoginMember.of(member);
    }

    @Transactional
    public void logout(LoginMember loginMember) {
        loginHistoryService.logout(loginMember);
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

    public MemberDto getMemberDto(Long memberId) {
        Member member = findMemberById(memberId);
        return MemberDto.of(member);
    }

    private Member findMemberById(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isEmpty()) {
            throw new InvalidMemberIdException();
        }
        return optionalMember.get();
    }

    @Transactional
    public LoginMember updateProfileImg(Long memberId, MultipartFile newFile) throws IOException {
        Member member = findMemberById(memberId);

        Attachment oldFile = member.getProfileImg();
        if (oldFile != null) {
            attachmentService.delete(oldFile);
        }

        if (newFile.isEmpty()) {
            member.changeProfileImg(null);
        } else {
            Attachment newFIle = attachmentService.save(newFile);
            member.changeProfileImg(newFIle);
        }

        return LoginMember.of(member);
    }

    public boolean checkPassword(Long memberId, String inputPassword) {
        Member member = findMemberById(memberId);
        return inputPassword.equals(member.getPassword());
    }

    @Transactional
    public void updatePassword(Long memberId, String currentPwd, String newPwd) {
        Member member = validateCurrentPwd(memberId, currentPwd);
        member.changePassword(newPwd);
    }

    private Member validateCurrentPwd(Long memberId, String currentPwd) {
        Member member = findMemberById(memberId);
        if (!currentPwd.equals(member.getPassword())) {
            throw new PasswordNotMatchedException();
        }
        return member;
    }

    @Transactional
    public void delete(Long memberId, String currentPwd) {
        Member member = validateCurrentPwd(memberId, currentPwd);
        validateRole(member);

        member.changeStatus(MemberStatus.RESIGNED);
    }

    private void validateRole(Member member) {
        if (member.getRole() == MemberRole.ADMIN) {
            throw new AdminCannotBeDeletedException();
        }
    }

    public MemberUpdateForm updateForm(Long memberId) {
        Member member = findMemberById(memberId);
        return MemberUpdateForm.of(member);
    }

    @Transactional
    public LoginMember update(MemberUpdateForm form) {
        validateUpdate(form);

        Member member = findMemberById(form.getId());
        // TODO 이렇게 address를 조회하고 넣어줘야하는게 번거롭다. 주소 구조를 다시 바꿔야 하나?..
        Address address = addressService.findByLevelIds(form.getAddressLv1(), form.getAddressLv2(), form.getAddressLv3());
        member.update(form, address);
        return LoginMember.of(member);
    }

    private void validateUpdate(MemberUpdateForm form) {
        Optional<Member> optionalMember = memberRepository.findByPhone(form.getPhone());
        if (optionalMember.isPresent() && !optionalMember.get().getId().equals(form.getId())) {
            throw new DuplicatedPhoneException();
        }
        // TODO 다른 검증도 해야하나? ex) readonly였던 이메일 등..
    }

    public Page<MemberListDto> getMemberListDtos(Pageable pageable) {
        return memberRepository.findAll(pageable).map(m -> MemberListDto.of(m));
    }

}
