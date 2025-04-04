package com.club.badminton.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ClubController {

    @GetMapping("/clubs/{id}/detail")
    public String clubList(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

    @GetMapping("/clubs/{id}/board")
    public String clubBoard(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

    @GetMapping("/clubs/{id}/album")
    public String clubAlbum(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

    @GetMapping("/clubs/{id}/schedule")
    public String clubSchedule(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

    @GetMapping("/clubs/{id}/budget")
    public String clubBudget(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

    @GetMapping("/clubs/{id}/trade")
    public String clubTrade(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

    @GetMapping("/clubs/{id}/lesson")
    public String clubLesson(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

}
