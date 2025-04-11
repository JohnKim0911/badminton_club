package com.club.badminton.controller.web.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {

    /**
     * 모든 controller에서 현재 url를 view쪽으로 넘겨주는 역할.
     * bodyHeader에서 선택된 메뉴 하이라이트시 사용됨.
     */
    @ModelAttribute("currentPath")
    public String currentPath(HttpServletRequest request) {
        return request.getRequestURI();
    }
}
