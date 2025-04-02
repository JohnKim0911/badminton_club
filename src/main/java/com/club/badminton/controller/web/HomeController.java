package com.club.badminton.controller.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request, HttpSession session) {
        if (session.getAttribute("loginMember") == null) {
            return "guestHome";
        } else {
            modelAddAttributeCurrentPath(model, request);
            return "memberHome";
        }
    }

    //메뉴 테스트용
    @GetMapping("/dummy1")
    public String dummy1(Model model, HttpServletRequest request) {
        modelAddAttributeCurrentPath(model, request);
        return "guestHome";
    }

    @GetMapping("/dummy2")
    public String dummy2(Model model, HttpServletRequest request) {
        modelAddAttributeCurrentPath(model, request);
        return "guestHome";
    }

    @GetMapping("/dummy3")
    public String dummy3(Model model, HttpServletRequest request) {
        modelAddAttributeCurrentPath(model, request);
        return "guestHome";
    }

    public static void modelAddAttributeCurrentPath(Model model, HttpServletRequest request) {
        //상단 menubar에서 선택된 메뉴 표시할 때 사용됨
        model.addAttribute("currentPath", request.getRequestURI());
    }
}
