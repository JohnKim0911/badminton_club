package com.club.badminton.controller.web;

import com.club.badminton.dto.member.*;
import com.club.badminton.exception.address.InvalidAddressIdException;
import com.club.badminton.exception.attachment.FileTooBigException;
import com.club.badminton.exception.attachment.NoFileException;
import com.club.badminton.exception.member.login.ResignedMemberException;
import com.club.badminton.exception.member.signup.DuplicatedEmailException;
import com.club.badminton.exception.member.signup.DuplicatedPhoneException;
import com.club.badminton.exception.member.login.NotRegisteredEmailException;
import com.club.badminton.exception.member.login.PasswordNotMatchedException;
import com.club.badminton.exception.member.signup.NullAddressLv3Exception;
import com.club.badminton.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import static com.club.badminton.InitApp.ADDRESSES;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/new")
    public String signUpForm(Model model) {
        model.addAttribute("memberSignUpForm", new MemberSignUpForm());
        model.addAttribute("addressLv1List", ADDRESSES.getLv1List());
        model.addAttribute("addressChildrenMap", ADDRESSES.getChildrenMap());
        return "members/signUpForm";
    }

    @PostMapping("/new")
    public String signUp(@Valid MemberSignUpForm form, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("addressLv1List", ADDRESSES.getLv1List());
            model.addAttribute("addressChildrenMap", ADDRESSES.getChildrenMap());
            return "members/signUpForm";
        }

        //TODO 이렇게 예외처리하는게 좋은 방법인지?..
        try {
            memberService.signUp(form);
            redirectAttributes.addFlashAttribute("popUpMessage", "회원가입에 성공하였습니다.");
            return "redirect:/members/login";

        } catch (DuplicatedEmailException | DuplicatedPhoneException | NullAddressLv3Exception |
                 InvalidAddressIdException e) {
            //회원가입 실패
            model.addAttribute("addressLv1List", ADDRESSES.getLv1List());
            model.addAttribute("addressChildrenMap", ADDRESSES.getChildrenMap());
            handleException(e, bindingResult);
            return "members/signUpForm";
        }
    }

    private void handleException(RuntimeException e, BindingResult bindingResult) {
        if (e instanceof DuplicatedEmailException) {
            bindingResult.rejectValue("email", "duplicated.email", e.getMessage());
        } else if (e instanceof DuplicatedPhoneException) {
            bindingResult.rejectValue("phone", "duplicated.phone", e.getMessage());
        } else if (e instanceof NullAddressLv3Exception) {
            bindingResult.rejectValue("addressLv3", "required.addressLv3", e.getMessage());
        } else if (e instanceof InvalidAddressIdException) {
            bindingResult.rejectValue("addressLv3", "invalid.addressLv3", e.getMessage());
        }
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "members/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm, HttpSession session, Model model) {

        try {
            LoginMember loginMember = memberService.login(loginForm);
            session.setAttribute("loginMember", loginMember);
            return "redirect:/";

        } catch (NotRegisteredEmailException | PasswordNotMatchedException | ResignedMemberException e) {
            //로그인 실패
            model.addAttribute("popUpMessage", e.getMessage());
            return "members/login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/{id}/detail")
    public String detail(@PathVariable Long id, Model model) {
        MemberUpdateForm form = memberService.updateForm(id);
//        Map<Integer, AddressDto> addressDtoMapByDepth = addressService.getRelatedDtoMapByDepth(form.getAddressId());
        model.addAttribute("memberUpdateForm", form);
//        model.addAttribute("addressDtoMapByDepth", addressDtoMapByDepth);
        return "members/memberDetail";
    }

    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable Long id, Model model) {
        MemberUpdateForm form = memberService.updateForm(id);
        model.addAttribute("memberUpdateForm", form);
//        model.addAttribute("addressDtoMapByDepth", addressService.getRelatedDtoMapByDepth(form.getAddressId()));
//        model.addAttribute("addressByDepth1List", addressService.getDtoListByDepth(1));
//        model.addAttribute("childrenAddressMap", addressService.getChildrenDtoMap());
        return "members/memberUpdate";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @Valid MemberUpdateForm form, BindingResult bindingResult,
                         HttpSession session, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "members/memberUpdate";
        }

        try {
            LoginMember loginMember = memberService.update(form);
            session.setAttribute("loginMember", loginMember);
            redirectAttributes.addFlashAttribute("popUpMessage", "성공적으로 회원정보를 수정하였습니다.");
            return "redirect:/members/" + id + "/detail";

        } catch (DuplicatedPhoneException e) {
            //수정 실패
            handleException(e, bindingResult);
            return "members/memberUpdate";
        }
    }

    @PostMapping("/{id}/checkPassword")
    public @ResponseBody String checkPassword(@PathVariable Long id, @RequestParam("currentPassword") String password) {
        boolean isMatched = memberService.checkPassword(id, password);
        return isMatched ? "ok" : "wrong";
    }

    @PostMapping("/{id}/updatePassword")
    public String updatePassword(@PathVariable Long id, @RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword, RedirectAttributes redirectAttributes) {

        //TODO 비밀번호 이중으로 검증?
        memberService.updatePassword(id, newPassword);

        redirectAttributes.addFlashAttribute("popUpMessage", "성공적으로 비밀번호를 변경하였습니다.");
        return "redirect:/members/" + id + "/detail";
    }

    @PostMapping("/{id}/updateProfileImg")
    public String updateProfileImg(@PathVariable Long id,
                                   @RequestParam(value = "resetToDefault", required = false) boolean resetToDefault,
                                   @RequestParam(value = "attachment", required = false) MultipartFile file,
                                   HttpSession session, RedirectAttributes redirectAttributes) {

        if (resetToDefault) {
            handleProfileImgReset(id, session, redirectAttributes);
        } else {
            handleProfileImgUpload(id, file, session, redirectAttributes);
        }

        return "redirect:/members/" + id + "/detail";
    }

    private void handleProfileImgReset(Long memberId, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            LoginMember loginMember = memberService.setDefaultProfileImg(memberId);
            session.setAttribute("loginMember", loginMember);
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("popUpMessage", "파일 삭제 중 오류가 발생했습니다.");
        }
    }

    private void handleProfileImgUpload(Long memberId, MultipartFile file, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            LoginMember loginMember = memberService.updateProfileImg(memberId, file);
            session.setAttribute("loginMember", loginMember);
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("popUpMessage", "파일 업로드 중 오류가 발생했습니다.");
        } catch (NoFileException | FileTooBigException e) {
            redirectAttributes.addFlashAttribute("popUpMessage", e.getMessage());
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        memberService.delete(id);
        session.invalidate();
        redirectAttributes.addFlashAttribute("popUpMessage", "회원탈퇴에 성공하였습니다. 그 동안 이용해주셔서 감사합니다.");
        //TODO 탈퇴회원 복구 기능 추가?
        return "redirect:/members/login";
    }

}
