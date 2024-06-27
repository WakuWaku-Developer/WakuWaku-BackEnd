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
import java.util.Optional;

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
            log.error("OAuth 멤버 정보 가져오기 중 오류 발생: {}", e.getMessage(), e);
            throw WakuWakuException.FAILED_TO_LOGIN;
        }

        Optional<Member> optionalMember;
        try {
            optionalMember = memberRepository.findByEmail(oauthMember.getEmail());
        } catch (Exception e) {
            log.error("회원 정보 조회 중 오류 발생: {}", e.getMessage(), e);
            throw WakuWakuException.FAILED_TO_LOGIN;
        }

        Member member = optionalMember.orElseGet(() -> Member.builder()
                .oauthServerId(oauthMember.getOauthId().getOauthServerId())
                .oauthServerType(oauthMember.getOauthId().getOauthServerType())
                .email(oauthMember.getEmail())
                .birthday(oauthMember.getBirthday())
                .nickname(oauthMember.getNickname())
                .profileImageUrl(oauthMember.getProfileImageUrl())
                .role(Role.USER)
                .build());

        if (!optionalMember.isPresent() || "N".equals(member.getCheckStatus())) {
            member.setCheckStatus("Y");
            try {
                memberRepository.save(member);
            } catch (Exception e) {
                log.error("회원 정보 저장 중 오류 발생: {}", e.getMessage(), e);
                throw WakuWakuException.FAILED_TO_LOGIN;
            }
        }

        Map<String, Long> response = new HashMap<>();
        response.put("id", member.getId());
        return response;
    }

}
