package com.example.oauth.member.controller;

import com.example.oauth.member.dto.MemberResponseDto;
import com.example.oauth.member.service.MemberService;
import com.example.oauth.util.security.auth.LoginMember;
import com.example.oauth.util.security.auth.SessionMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/info")
    public ResponseEntity<MemberResponseDto> hello(@LoginMember SessionMember sessionMember) {
        MemberResponseDto memberInfoById = memberService.getMemberInfoById(
            sessionMember.getMemberId());
        return ResponseEntity.ok(memberInfoById);
    }


}
