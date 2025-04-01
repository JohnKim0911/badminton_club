package com.club.badminton.controller.web;

import com.club.badminton.dto.member.*;
import com.club.badminton.exception.validation.signup.DuplicatedEmailException;
import com.club.badminton.exception.validation.signup.DuplicatedPhoneException;
import com.club.badminton.exception.validation.login.NotRegisteredEmailException;
import com.club.badminton.exception.validation.login.PasswordNotMatchedException;
import com.club.badminton.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.club.badminton.controller.web.HomeController.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String signUpForm(Model model) {
        model.addAttribute("memberSignUpForm", new MemberSignUpForm());
        return "members/signUpForm";
    }

    @PostMapping("/members/new")
    public String signUp(@Valid MemberSignUpForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "members/signUpForm";
        }

        try {
            memberService.signUp(form);
        } catch (DuplicatedEmailException | DuplicatedPhoneException e) {
            //회원가입 실패
            handleSignUpDuplicateException(e, bindingResult);
            return "members/signUpForm";
        }

        redirectAttributes.addFlashAttribute("popUpMessage", "회원가입에 성공하였습니다.");
        return "redirect:/login";
    }

    private void handleSignUpDuplicateException(RuntimeException e, BindingResult bindingResult) {
        if (e instanceof DuplicatedEmailException) {
            bindingResult.rejectValue("email", "duplicated.email", e.getMessage());
        } else if (e instanceof DuplicatedPhoneException) {
            bindingResult.rejectValue("phone", "duplicated.phone", e.getMessage());
        }
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "members/login";
    }

    //TODO restAPI 형식으로 URL 수정필요
    @PostMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm, HttpSession session, Model model) {
        try {
            LoginMember loginMember = memberService.login(loginForm);
            session.setAttribute("loginMember", loginMember);
        } catch (NotRegisteredEmailException | PasswordNotMatchedException e) {
            //로그인 실패
            model.addAttribute("popUpMessage", e.getMessage());
            return "members/login";
        }
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/members")
    public String memberList(Model model, HttpServletRequest request) {
        List<MemberDto> memberDtos = memberService.findMembers();
        model.addAttribute("members", memberDtos);
        modelAddAttributeCurrentPath(model, request);
        return "members/memberList";
    }

    @GetMapping("/myPage")
    public String myPage(HttpSession session, Model model) {
        LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
        MemberUpdateForm form = memberService.updateForm(loginMember.getId());
        model.addAttribute("memberUpdateForm", form);
        return "members/myPage";
    }

    @GetMapping("/members/update")
    public String updateForm(HttpSession session, Model model) {
        LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
        MemberUpdateForm form = memberService.updateForm(loginMember.getId());
        model.addAttribute("memberUpdateForm", form);
        return "members/memberUpdate";
    }

    @PostMapping("/members/update")
    public String update(@Valid MemberUpdateForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "members/memberUpdate";
        }

        try {
            memberService.update(form);
        } catch (DuplicatedPhoneException e) {
            //수정 실패
            handleSignUpDuplicateException(e, bindingResult);
            return "members/memberUpdate";
        }

        redirectAttributes.addFlashAttribute("popUpMessage", "성공적으로 회원정보를 수정하였습니다.");
        return "redirect:/myPage";
    }

    @PostMapping("/member/checkPassword")
    public @ResponseBody String checkPassword(@RequestParam("currentPassword") String password, HttpSession session) {
        LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
        boolean isMatched = memberService.checkPassword(loginMember.getId(), password);
        return isMatched ? "ok" : "wrong";
    }

    @PostMapping("/members/updatePassword")
    public String updatePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 HttpSession session, RedirectAttributes redirectAttributes) {

        LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
        boolean isMatched = memberService.checkPassword(loginMember.getId(), currentPassword);

        if (isMatched) {
            memberService.updatePassword(loginMember.getId(), newPassword);
            redirectAttributes.addFlashAttribute("popUpMessage", "성공적으로 비밀번호를 변경하였습니다.");
        } else {
            redirectAttributes.addFlashAttribute("popUpMessage", "기존 비밀번호가 틀립니다.");
        }
        return "redirect:/myPage";
    }

}
