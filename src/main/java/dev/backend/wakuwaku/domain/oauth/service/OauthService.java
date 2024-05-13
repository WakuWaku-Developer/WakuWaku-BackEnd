package dev.backend.wakuwaku.domain.oauth.service;

import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.member.repository.MemberRepository;
import dev.backend.wakuwaku.domain.oauth.dto.OauthMember;
import dev.backend.wakuwaku.domain.oauth.dto.Role;
import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import dev.backend.wakuwaku.domain.client.OauthMemberClientComposite;
import dev.backend.wakuwaku.domain.oauth.oauthcode.AuthCodeRequestUrlProviderComposite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * OauthServerType을 받아서 해당 인증 서버에서 Auth Code를 받아오기 위한 URL 주소를 생성
 */
@Service
@RequiredArgsConstructor
public class OauthService {

    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final OauthMemberClientComposite oauthMemberClientComposite;

    private final MemberRepository memberRepository;

    public String getAuthCodeRequestUrl(OauthServerType oauthServerType) {
        return authCodeRequestUrlProviderComposite.provide(oauthServerType);
    }

    /*

    public Long login(OauthServerType oauthServerType, String authCode) {
        OauthMember oauthMember = oauthMemberClientComposite.fetch(oauthServerType, authCode);
        Member memberSaved = memberRepository.findByOauthId(oauthMember.oauthId())
                .orElseGet(() -> memberRepository.save(oauthMember));
        return memberSaved.getId();
    }


     */

    public Long login(OauthServerType oauthServerType, String authCode) {
        OauthMember oauthMember = oauthMemberClientComposite.fetch(oauthServerType, authCode);

        // OauthMember 객체로부터 필요한 정보를 추출하여 Member 객체를 생성
        Member member = new Member();
        member.setOauthServerId(oauthMember.getOauthId().oauthServerId());
        member.setOauthServerType(oauthMember.getOauthId().oauthServerType());
        member.setOauthId(oauthMember.getOauthId());
        member.setEmail(oauthMember.getEmail());
        member.setBirthday(oauthMember.getBirthday());
        member.setNickname(oauthMember.getNickname());
        member.setProfileImageUrl(oauthMember.getProfileImageUrl());
        member.setRole(Role.USER);

        System.out.println(oauthMember.getEmail()+"<이메일 : 생일>"+ oauthMember.getBirthday());
        // 생성된 Member 객체를 저장
        Member memberSaved = memberRepository.save(member);

        return memberSaved.getId();
    }
}