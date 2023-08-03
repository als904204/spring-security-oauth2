package com.example.oauth.util.security.oauth.handler;

import com.example.oauth.member.entity.Role;
import com.example.oauth.util.security.oauth.entity.CustomOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        log.info("OAuth2SuccessHandler 실행");


        CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
        String email = principal.getEmail();
        System.out.println("email = " + email);
        Authentication securityUser = createAuthenticationForUser(principal);

        SecurityContextHolder.getContext().setAuthentication(securityUser);



    }

    public Authentication createAuthenticationForUser(CustomOAuth2User principal) {
        String email = principal.getEmail();
        List<SimpleGrantedAuthority> role = List.of(new SimpleGrantedAuthority(principal.getRole().getKey()));
        String sub = principal.getAttribute("sub");

        return new UsernamePasswordAuthenticationToken(email, sub,role);
    }
}
