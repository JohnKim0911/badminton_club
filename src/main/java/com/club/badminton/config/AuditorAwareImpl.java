package com.club.badminton.config;

import com.club.badminton.dto.member.LoginMember;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Long> {

    /**
     * 세션에서 로그인 유저 정보 가져옴.
     * BaseEntity의 CreatedBy, lastModifiedBy에 자동주입.
     */
    @Override
    public Optional<Long> getCurrentAuditor() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            // Return a default ID or empty if no session is available
            return Optional.empty();
        }

        HttpSession session = attributes.getRequest().getSession(false); //false means: “Do NOT create a new session if one doesn’t already exist.”
        if (session == null) {
            return Optional.empty();
        }

        LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
        if (loginMember == null) {
            return Optional.empty();
        }

        return Optional.of(loginMember.getId());
    }
}


