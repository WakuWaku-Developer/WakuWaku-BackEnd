package dev.backend.wakuwaku.domain.oauth.service;

import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.member.repository.MemberRepository;
import dev.backend.wakuwaku.domain.oauth.dto.OauthMember;
import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import dev.backend.wakuwaku.domain.oauth.dto.Role;
import dev.backend.wakuwaku.global.exception.WakuWakuException;
import dev.backend.wakuwaku.global.infra.oauth.client.OauthMemberClientComposite;
import dev.backend.wakuwaku.global.infra.oauth.oauthcode.OauthCodeRequestUrlProviderComposite;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OauthService {

    private final OauthCodeRequestUrlProviderComposite oauthCodeRequestUrlProviderComposite;
    private final OauthMemberClientComposite oauthMemberClientComposite;
    private final MemberRepository memberRepository;

    public String getAuthCodeRequestUrl(OauthServerType oauthServerType) {
        return oauthCodeRequestUrlProviderComposite.provide(oauthServerType);
    }

    public Map<String, Long> login(OauthServerType oauthServerType, String authCode) {
        OauthMember oauthMember;
        try {
            oauthMember = oauthMemberClientComposite.fetch(oauthServerType, authCode);
        } catch (Exception e) {
            log.error("Failed to fetch OAuth member: {}", e.getMessage(), e);
            throw WakuWakuException.FAILED_TO_LOGIN;
        }

        Member member = memberRepository.findByEmail(oauthMember.getEmail())
                .orElseGet(
                        () -> memberRepository.save(
                                Member.builder()
                                        .oauthServerId(oauthMember.getOauthId().getOauthServerId())
                                        .oauthServerType(oauthMember.getOauthId().getOauthServerType())
                                        .email(oauthMember.getEmail())
                                        .birthday(oauthMember.getBirthday())
                                        .nickname(oauthMember.getNickname())
                                        .profileImageUrl(oauthMember.getProfileImageUrl())
                                        .role(Role.USER)
                                        .build()
                        )
                );

        if ("N".equals(member.getCheckStatus())) {
            member.updateCheckstatus("Y");
        }

        Map<String, Long> response = new HashMap<>();
        response.put("id", member.getId());
        return response;
    }
}
