package com.example.oauth.member.controller;

import com.example.oauth.util.security.auth.LoginMember;
import com.example.oauth.util.security.auth.SessionMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

    @GetMapping("/info")
    public String hello(@LoginMember SessionMember sessionMember) {
        return "hello";
    }


}
