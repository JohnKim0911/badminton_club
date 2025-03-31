package com.club.badminton.controller.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        modelAddAttributeCurrentPath(model, request);
        return "home";
    }

    //메뉴 테스트용
    @GetMapping("/dummy1")
    public String dummy1(Model model, HttpServletRequest request) {
        modelAddAttributeCurrentPath(model, request);
        return "home";
    }

    @GetMapping("/dummy2")
    public String dummy2(Model model, HttpServletRequest request) {
        modelAddAttributeCurrentPath(model, request);
        return "home";
    }

    @GetMapping("/dummy3")
    public String dummy3(Model model, HttpServletRequest request) {
        modelAddAttributeCurrentPath(model, request);
        return "home";
    }

    public static void modelAddAttributeCurrentPath(Model model, HttpServletRequest request) {
        model.addAttribute("currentPath", request.getRequestURI());
    }
}
