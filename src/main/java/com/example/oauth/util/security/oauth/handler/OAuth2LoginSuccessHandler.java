package com.example.oauth.util.security.oauth.handler;

import com.example.oauth.member.entity.Role;
import com.example.oauth.util.security.oauth.entity.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2SuccessHandler 실행");

        CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
        if (principal.getRole() == Role.GUEST) {
            log.info("첫 회원가입 유저 : 닉네임 설정페이지로 이동");
            response.sendRedirect("/oauth2/sign-up");
        }else{
            log.info("이미 가입한 유저");
            // 시큐리티 세션추가
        }
    }
}
