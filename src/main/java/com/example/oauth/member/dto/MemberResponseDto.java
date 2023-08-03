package com.example.oauth.member.dto;

import com.example.oauth.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDto {


    private Long memberId;
    private String nickname;
    private String email;
    private String imageUrl;

    public MemberResponseDto(Member member) {
        this.memberId = member.getId();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.imageUrl = member.getImageUrl();
    }

}
