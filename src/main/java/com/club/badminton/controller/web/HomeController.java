package com.club.badminton.controller.web;

import jakarta.servlet.http.HttpServletRequest;
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

    public static void modelAddAttributeCurrentPath(Model model, HttpServletRequest request) {
        model.addAttribute("currentPath", request.getRequestURI());
    }
}
