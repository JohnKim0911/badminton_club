package com.club.badminton.controller.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClubController {

    @GetMapping("/clubs")
    public String clubList(Model model, HttpServletRequest request) {
        return "clubs/clubList";
    }
}
