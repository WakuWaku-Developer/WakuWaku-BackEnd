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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    public Map<String, Long> login(OauthServerType oauthServerType, String authCode) throws WakuWakuException {
        try {
            OauthMember oauthMember = oauthMemberClientComposite.fetch(oauthServerType, authCode);

            Optional<Member> optionalMember = memberRepository.findByEmail(oauthMember.getEmail());

            Member member;
            if (optionalMember.isPresent()) {
                member = optionalMember.get();
                if (member.getCheckStatus().equals("N")) {
                    member.setCheckStatus("Y");
                    memberRepository.save(member);
                }
            } else {
                // 새로운 회원 생성
                member = Member.builder()
                        .oauthServerId(oauthMember.getOauthId().getOauthServerId())
                        .oauthServerType(oauthMember.getOauthId().getOauthServerType())
                        .email(oauthMember.getEmail())
                        .birthday(oauthMember.getBirthday())
                        .nickname(oauthMember.getNickname())
                        .profileImageUrl(oauthMember.getProfileImageUrl())
                        .role(Role.USER)
                        .build();
                // 새로운 회원 저장
                memberRepository.save(member);
            }

            Map<String, Long> response = new HashMap<>();
            response.put("id", member.getId());
            return response;

        } catch (Exception e) {
            log.error("로그인 중 오류 발생: {}", e.getMessage(), e);
            throw WakuWakuException.FAILED_TO_LOGIN;
        }
    }
}
