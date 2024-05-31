package dev.backend.wakuwaku.domain.oauth.service;

import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.member.repository.MemberRepository;
import dev.backend.wakuwaku.domain.oauth.dto.OauthMember;
import dev.backend.wakuwaku.domain.oauth.dto.Role;
import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import dev.backend.wakuwaku.global.infra.oauth.client.OauthMemberClientComposite;
import dev.backend.wakuwaku.domain.oauth.oauthcode.AuthCodeRequestUrlProviderComposite;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OauthService {

    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final OauthMemberClientComposite oauthMemberClientComposite;
    private final MemberRepository memberRepository;

    public String getAuthCodeRequestUrl(OauthServerType oauthServerType) {
        return authCodeRequestUrlProviderComposite.provide(oauthServerType);
    }

    public Long login(OauthServerType oauthServerType, String authCode) {
        try {
            OauthMember oauthMember = oauthMemberClientComposite.fetch(oauthServerType, authCode);

            Member member = memberRepository.findByoauthServerId(oauthMember.getOauthId().oauthServerId())
                    .orElseGet(() -> {
                        Member newMember = new Member();
                        newMember.setOauthServerId(oauthMember.getOauthId().oauthServerId());
                        newMember.setOauthServerType(oauthMember.getOauthId().oauthServerType());
                        newMember.setOauthId(oauthMember.getOauthId());
                        newMember.setEmail(oauthMember.getEmail());
                        newMember.setBirthday(oauthMember.getBirthday());
                        newMember.setNickname(oauthMember.getNickname());
                        newMember.setProfileImageUrl(oauthMember.getProfileImageUrl());
                        newMember.setRole(Role.USER);
                        return newMember;
                    });


            member = memberRepository.save(member);
            return member.getId();

        } catch (Exception e) {
            log.error("로그인 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("로그인 중 오류가 발생했습니다. 다시 시도해 주세요.");
        }
    }

}
