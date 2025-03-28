package com.club.badminton.controller.web;

import com.club.badminton.dto.LoginForm;
import com.club.badminton.dto.MemberDto;
import com.club.badminton.dto.MemberSignUpForm;
import com.club.badminton.exception.validation.signup.DuplicatedEmailException;
import com.club.badminton.exception.validation.signup.DuplicatedPhoneException;
import com.club.badminton.exception.validation.login.NotRegisteredEmailException;
import com.club.badminton.exception.validation.login.PasswordNotMatchedException;
import com.club.badminton.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
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
            redirectAttributes.addFlashAttribute("signupSuccess", "회원가입에 성공하였습니다.");
            return "redirect:/members/login";

        } catch (DuplicatedEmailException | DuplicatedPhoneException e) {
            //회원가입 실패
            handleSignUpDuplicateException(e, bindingResult);
            return "members/signUpForm";
        }
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

    @PostMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm, HttpSession session, Model model) {
        try {
            MemberDto loginMember = memberService.login(loginForm);
            session.setAttribute("loginMember", loginMember);
            return "redirect:/";

        } catch (NotRegisteredEmailException | PasswordNotMatchedException e) {
            //로그인 실패
            model.addAttribute("errorMessage", e.getMessage());
            return "members/login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/members")
    public String memberList(Model model) {
        List<MemberDto> memberDtos = memberService.findMembers();
        model.addAttribute("members", memberDtos);
        return "members/memberList";
    }
}
