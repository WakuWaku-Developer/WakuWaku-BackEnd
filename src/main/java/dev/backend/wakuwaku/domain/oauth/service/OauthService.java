package dev.backend.wakuwaku.domain.oauth.service;

import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.member.repository.MemberRepository;
import dev.backend.wakuwaku.domain.oauth.dto.OauthMember;
import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import dev.backend.wakuwaku.domain.oauth.dto.Role;
import dev.backend.wakuwaku.global.exception.ExceptionStatus;
import dev.backend.wakuwaku.global.infra.oauth.client.OauthMemberClientComposite;
import dev.backend.wakuwaku.global.infra.oauth.oauthcode.OauthCodeRequestUrlProviderComposite;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
public class OauthService {

    private final OauthCodeRequestUrlProviderComposite oauthCodeRequestUrlProviderComposite;
    private final OauthMemberClientComposite oauthMemberClientComposite;
    private final MemberRepository memberRepository;

    public String getAuthCodeRequestUrl(OauthServerType oauthServerType) {
        return oauthCodeRequestUrlProviderComposite.provide(oauthServerType);
    }

    public Map<String, Long> login(OauthServerType oauthServerType, String authCode) {
        try {
            OauthMember oauthMember = oauthMemberClientComposite.fetch(oauthServerType, authCode);

            Member member = memberRepository.findByEmail(oauthMember.getEmail())
                    .orElse(null);

            if (member == null) {
                // 새로운 회원 생성
                member = new Member();
                member.setOauthServerId(oauthMember.getOauthId().oauthServerId());
                member.setOauthServerType(oauthMember.getOauthId().oauthServerType());
                member.setEmail(oauthMember.getEmail());
                member.setBirthday(oauthMember.getBirthday());
                member.setNickname(oauthMember.getNickname());
                member.setProfileImageUrl(oauthMember.getProfileImageUrl());
                member.setRole(Role.USER);

                // 새로운 회원 저장
                member = memberRepository.save(member);
            }

            Map<String, Long> response = new HashMap<>();
            response.put("id", member.getId());
            return response;

        } catch (Exception e) {
            log.error("로그인 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException(ExceptionStatus.FALIED_TO_LOGIN.getMessage(), e);
        }
    }
}
