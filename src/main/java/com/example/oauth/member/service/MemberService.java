package com.example.oauth.member.service;

import com.example.oauth.member.dto.MemberResponseDto;
import com.example.oauth.member.entity.Member;
import com.example.oauth.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    
    public MemberResponseDto getMemberInfoById(Long id) {
        Member member = getMemberById(id);
        return new MemberResponseDto(member);
    }

    private Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("존재하지 않는 회원 ID : " + id)
        );
    }

}
