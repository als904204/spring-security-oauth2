package com.example.oauth.util.security;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import com.example.oauth.util.security.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                .requestMatchers(toH2Console()).permitAll()
                .anyRequest().permitAll()
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/loginForm")
                .defaultSuccessUrl("/")
                .userInfoEndpoint(point -> point
                    .userService(customOAuth2UserService))
            )
            .headers(headers->headers
                .frameOptions(FrameOptionsConfig::disable)
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers(toH2Console())
            );


        return http.build();
    }
}
