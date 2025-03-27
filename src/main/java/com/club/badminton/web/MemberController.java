package com.club.badminton.web;

import com.club.badminton.dto.MemberDto;
import com.club.badminton.entity.Member;
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
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        try {
            Member member = MemberForm.toMember(form);
            memberService.join(member); //IllegalStateException 발생 가능 - ex) 이미 사용중인 이메일입니다.
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "members/createMemberForm";
        }

        redirectAttributes.addFlashAttribute("signupSuccess", "회원가입에 성공하였습니다.");
        return "redirect:/members/login";
    }

    @GetMapping("/members/login")
    public String loginForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "members/login";
    }

    @PostMapping("/members/login")
    public String login(@ModelAttribute MemberDto memberDto, HttpSession session, Model model) {
        try {
            MemberDto loginMember = memberService.login(memberDto); //IllegalStateException 발생 가능

            //로그인 성공
            session.setAttribute("loginMember", loginMember);
            return "redirect:/";
        } catch (IllegalStateException e) {
            //로그인 실패 - ex) 등록되지 않은 이메일입니다. or 비밀번호가 일치하지 않습니다.
            model.addAttribute("errorMessage", e.getMessage());
            return "/members/login";
        }
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<MemberDto> memberDtos = memberService.findMembers();
        model.addAttribute("members", memberDtos);
        return "members/memberList";
    }
}
