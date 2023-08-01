package com.example.oauth.util.security.oauth.attributes;

import com.example.oauth.member.entity.Member;
import com.example.oauth.member.entity.Role;
import com.example.oauth.member.entity.SocialType;
import com.example.oauth.util.security.oauth.attributes.userInfo.GoogleOAuth2UserInfo;
import com.example.oauth.util.security.oauth.attributes.userInfo.OAuth2UserInfo;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

/**
 * 각 소셜에서 받아오는 데이터가 다르므로
 * 소셜별로 데이터를 받는 데이터를 분기 처리하는 DTO 클래스
 */
@Getter
public class OAuthAttributes {
    private String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
    private OAuth2UserInfo oauth2UserInfo;
    @Builder
    public OAuthAttributes(String nameAttributeKey,OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    public static OAuthAttributes of(
        SocialType socialType,
        String userNameAttributeName,
        Map<String, Object> attributes) {

        if (socialType == SocialType.NAVER) {
            return null;
        }
        if (socialType == SocialType.KAKAO) {
            return null;
        }
        if (socialType == SocialType.GITHUB) {
            return null;
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    public static OAuthAttributes ofGoogle(
        String userNameAttributeName,
        Map<String, Object> attributes) {
        return OAuthAttributes.builder()
            .nameAttributeKey(userNameAttributeName)
            .oauth2UserInfo(new GoogleOAuth2UserInfo(attributes))
            .build();
    }

    public Member toEntity(SocialType socialType, OAuth2UserInfo oauth2UserInfo) {
        return Member.builder()
            .socialType(socialType)
            .socialId(oauth2UserInfo.getId())
            .email(UUID.randomUUID() + "@socialUser.com")
            .nickname(oauth2UserInfo.getNickname())
            .imageUrl(oauth2UserInfo.getImageUrl())
            .role(Role.GUEST)
            .build();
    }

}
