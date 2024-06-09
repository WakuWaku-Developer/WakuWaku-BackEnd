package dev.backend.wakuwaku.domain.member.service;

import dev.backend.wakuwaku.domain.member.dto.request.MemberUpdateRequest;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.member.repository.MemberRepository;
import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import dev.backend.wakuwaku.domain.oauth.dto.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    private static final Long MEMBER_ID = 1L;

    private Member member;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .oauthServerId("12345")
                .oauthServerType(OauthServerType.GOOGLE)
                .email("test@example.com")
                .birthday("1990-01-01")
                .nickname("testUser")
                .profileImageUrl("https://example.com/profile.jpg")
                .role(Role.USER)
                .build();
    }

    @Test
    @DisplayName("회원 정보 저장 성공")
    void saveSuccess() {
        // given
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.empty());
        given(memberRepository.save(any(Member.class))).willReturn(member);

        // when
        memberService.update(MEMBER_ID, new MemberUpdateRequest("newNickname", "newProfileUrl", "1991-01-01"));

        // then
        then(memberRepository).should().save(member);
    }

    @Test
    @DisplayName("중복된 이메일이 존재하면 회원 정보 저장 실패")
    void saveFailed() {
        // given
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(member));

        // when
        try {
            memberService.update(MEMBER_ID, new MemberUpdateRequest("newNickname", "newProfileUrl", "1991-01-01"));
        } catch (IllegalStateException e) {
            // then
            assertThat(e).isInstanceOf(IllegalStateException.class);
            then(memberRepository).should(never()).save(any(Member.class));
        }
    }

    @Test
    void findById() {
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

        // when
        Member foundMember = memberService.findById(MEMBER_ID);

        // then
        assertThat(foundMember).isEqualTo(member);
        then(memberRepository).should().findById(anyLong());
    }

    @Test
    void findAll() {
        // given
        List<Member> members = List.of(member);
        given(memberRepository.findAll()).willReturn(members);

        // when
        List<Member> foundMembers = memberService.findAll();

        // then
        assertThat(foundMembers).hasSize(1);
        assertThat(foundMembers.get(0)).isEqualTo(member);
        then(memberRepository).should().findAll();
    }

    @Test
    void updateMember() {
        // given
        MemberUpdateRequest updateRequest = new MemberUpdateRequest("newNickname", "newProfileUrl", "1991-01-01");
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

        // when
        memberService.update(MEMBER_ID, updateRequest);

        // then
        assertThat(member.getNickname()).isEqualTo(updateRequest.getNickname());
        assertThat(member.getProfileImageUrl()).isEqualTo(updateRequest.getProfileImageUrl());
        assertThat(member.getBirthday()).isEqualTo(updateRequest.getBirthday());
        then(memberRepository).should().save(member);
    }

    @Test
    void deleteById() {
        // when
        memberService.deleteById(MEMBER_ID);

        // then
        then(memberRepository).should().deleteById(MEMBER_ID);
    }
}
