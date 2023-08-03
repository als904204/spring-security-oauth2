package com.example.oauth.util.security.oauth.service;


import com.example.oauth.member.entity.Member;
import com.example.oauth.member.entity.SocialType;
import com.example.oauth.member.repository.MemberRepository;
import com.example.oauth.util.security.oauth.attributes.OAuthAttributes;
import com.example.oauth.util.security.oauth.entity.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private static final String NAVER = "naver";
    private static final String KAKAO = "kakao";
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // OAuth2 회사이름 google,KAKAO,NAVER
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인 시 키(PK)가 되는 값 (sub)
        String userNameAttributeName = userRequest
            .getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUserNameAttributeName();

        // 유저 정보 JSON
        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("attributes={}",attributes);

        // socialType 에 따라 유저 정보를 통해 OAuthAttributes 객체 생성
        SocialType socialType = getSocialType(registrationId);

        // 요청한 accessToken 의 해당 회원 정보를 객체화 한 클래스
        OAuthAttributes extractUserInfo = OAuthAttributes.of(socialType, userNameAttributeName,
            attributes);

        assert extractUserInfo != null;
        Member createdMember = getMember(extractUserInfo, socialType);



        return new CustomOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority(createdMember.getRole().getKey())),
            attributes,
            extractUserInfo.getNameAttributeKey(),
            createdMember.getEmail(),
            createdMember.getRole()
        );
    }

    private SocialType getSocialType(String registrationId) {
        if(NAVER.equals(registrationId)) {
            return SocialType.NAVER;
        }
        if(KAKAO.equals(registrationId)) {
            return SocialType.KAKAO;
        }
        return SocialType.GOOGLE;
    }

    private Member getMember(OAuthAttributes attributes, SocialType socialType) {
        String SocialId = attributes.getOauth2UserInfo().getId();
        Member findUser = memberRepository.findBySocialTypeAndSocialId(socialType, SocialId)
            .orElse(null);

        if(findUser == null) {
            return saveMember(attributes, socialType);
        }

        return findUser;
    }

    private Member saveMember(OAuthAttributes attributes, SocialType socialType) {
        Member createdMember = attributes.toEntity(socialType, attributes.getOauth2UserInfo());
        return memberRepository.save(createdMember);
    }
}
