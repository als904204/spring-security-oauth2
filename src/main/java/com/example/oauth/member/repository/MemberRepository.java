package com.example.oauth.member.repository;

import com.example.oauth.member.entity.Member;
import com.example.oauth.member.entity.SocialType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
}
