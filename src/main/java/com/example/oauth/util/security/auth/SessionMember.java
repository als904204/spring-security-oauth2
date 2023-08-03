package com.example.oauth.util.security.auth;

import com.example.oauth.member.entity.Member;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class SessionMember implements Serializable {
    private final String nickname;
    private final String email;
    private final String imageUrl;

    public SessionMember(Member member) {
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.imageUrl = member.getImageUrl();
    }
}
