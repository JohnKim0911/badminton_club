package com.club.badminton.controller.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // TODO Spring Security 적용 - 미로그인 유저 일부를 제외한 모든 url 접근 제한. CRSF 방지

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request, HttpSession session) {
        if (session.getAttribute("loginMember") == null) {
            return "guestHome";
        }

        return "memberHome";
    }
}
