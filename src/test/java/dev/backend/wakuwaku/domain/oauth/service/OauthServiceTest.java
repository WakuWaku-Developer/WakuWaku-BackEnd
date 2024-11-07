package dev.backend.wakuwaku.domain.oauth.service;

import dev.backend.wakuwaku.domain.Status;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.member.repository.MemberRepository;
import dev.backend.wakuwaku.domain.oauth.dto.OauthId;
import dev.backend.wakuwaku.domain.oauth.dto.OauthMember;
import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import dev.backend.wakuwaku.global.exception.ExceptionStatus;
import dev.backend.wakuwaku.global.exception.WakuWakuException;
import dev.backend.wakuwaku.global.infra.oauth.client.OauthMemberClientComposite;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class OauthServiceTest {
    @Mock
    private OauthMemberClientComposite oauthMemberClientComposite;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private OauthService oauthService;

    private static final String AUTH_CODE = "test_auth_code";

    private static final String EMAIL = "test@example.com";

    private static final String NICKNAME = "TestUser";

    private static final String PROFILE_IMAGE_URL = "https://example.com/profile.jpg";

    private static final String BIRTHDAY = "1990-01-01";

    @Test
    @DisplayName("로그인 성공 시 회원 정보 반환")
    void loginSuccess() {
        // Given
        OauthServerType oauthServerType = OauthServerType.KAKAO;

        OauthId oauthId = new OauthId("test_oauth_server_id", oauthServerType);

        OauthMember oauthMember = new OauthMember(oauthId, NICKNAME, PROFILE_IMAGE_URL, EMAIL, BIRTHDAY);

        Member newMember = Member.builder()
                                 .oauthServerId(oauthId.getOauthServerId())
                                 .oauthServerType(oauthId.getOauthServerType())
                                 .email(EMAIL)
                                 .birthday(BIRTHDAY)
                                 .nickname(NICKNAME)
                                 .profileImageUrl(PROFILE_IMAGE_URL)
                                 .build();

        given(oauthMemberClientComposite.fetch(oauthServerType, AUTH_CODE)).willReturn(oauthMember);
        given(memberRepository.findByEmail(EMAIL)).willReturn(Optional.empty());
        given(memberRepository.save(any(Member.class))).willReturn(newMember);

        // When
        Member member = oauthService.login(oauthServerType, AUTH_CODE);

        // Then
        assertThat(member).isNotNull();

        assertThat(member.getId()).isEqualTo(newMember.getId());
        assertThat(member.getOauthServerType()).isEqualTo(newMember.getOauthServerType());
        assertThat(member.getOauthServerId()).isEqualTo(newMember.getOauthServerId());
        assertThat(member.getEmail()).isEqualTo(newMember.getEmail());
        assertThat(member.getBirthday()).isEqualTo(newMember.getBirthday());
        assertThat(member.getNickname()).isEqualTo(newMember.getNickname());
        assertThat(member.getProfileImageUrl()).isEqualTo(newMember.getProfileImageUrl());
        assertThat(member.getRole()).isEqualTo(newMember.getRole());

        then(memberRepository).should().save(any(Member.class));
    }

    @Test
    @DisplayName("이미 존재하는 회원인 경우 기존 회원 정보 반환")
    void loginExistingMember() {
        // Given
        OauthServerType oauthServerType = OauthServerType.KAKAO;

        OauthId oauthId = new OauthId("dummy_oauth_server_id", oauthServerType);

        OauthMember oauthMember = new OauthMember(oauthId, NICKNAME, PROFILE_IMAGE_URL, EMAIL, BIRTHDAY);

        Member existingMember = Member.builder()
                                      .email(EMAIL)
                                      .nickname(NICKNAME)
                                      .profileImageUrl(PROFILE_IMAGE_URL)
                                      .birthday(BIRTHDAY)
                                      .build();

        existingMember.createId(1L);
        existingMember.createStatus(Status.INACTIVE);

        given(oauthMemberClientComposite.fetch(oauthServerType, AUTH_CODE)).willReturn(oauthMember);
        given(memberRepository.findByEmail(EMAIL)).willReturn(Optional.of(existingMember));

        // When
        Member member = oauthService.login(oauthServerType, AUTH_CODE);

        // Then
        assertThat(member).isNotNull();

        assertThat(member.getId()).isEqualTo(existingMember.getId());
        assertThat(member.getOauthServerType()).isEqualTo(existingMember.getOauthServerType());
        assertThat(member.getOauthServerId()).isEqualTo(existingMember.getOauthServerId());
        assertThat(member.getEmail()).isEqualTo(existingMember.getEmail());
        assertThat(member.getBirthday()).isEqualTo(existingMember.getBirthday());
        assertThat(member.getNickname()).isEqualTo(existingMember.getNickname());
        assertThat(member.getProfileImageUrl()).isEqualTo(existingMember.getProfileImageUrl());
        assertThat(member.getRole()).isEqualTo(existingMember.getRole());
        assertThat(existingMember.getStatus()).isEqualTo(Status.ACTIVE);

        then(memberRepository).should().findByEmail(EMAIL);
    }

    @Test
    @DisplayName("로그인 실패 시 예외 발생")
    void loginFailure() {
        // Given
        OauthServerType oauthServerType = OauthServerType.KAKAO;

        given(oauthMemberClientComposite.fetch(oauthServerType, AUTH_CODE)).willThrow(new RuntimeException("OAuth client error"));

        // When / Then
        assertThatThrownBy(() -> oauthService.login(oauthServerType, AUTH_CODE))
                .isInstanceOf(WakuWakuException.class)
                .hasFieldOrPropertyWithValue("status", ExceptionStatus.FALIED_TO_LOGIN);
    }
}
