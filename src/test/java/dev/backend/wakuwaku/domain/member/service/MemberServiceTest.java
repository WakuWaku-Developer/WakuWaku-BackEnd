package dev.backend.wakuwaku.domain.member.service;

import dev.backend.wakuwaku.domain.member.dto.request.MemberUpdateRequest;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.member.repository.MemberRepository;
import dev.backend.wakuwaku.global.exception.ExceptionStatus;
import dev.backend.wakuwaku.global.exception.WakuWakuException;
import org.junit.jupiter.api.Assertions;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    private Member member;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                //.id(1L)
                .email("test@example.com")
                .nickname("testUser")
                .profileImageUrl("https://example.com/profile.jpg")
                .birthday("1990-01-01")
                .build();
    }





    @Test
    @DisplayName("모든 회원 조회")
    void findAll() {
        // given
        given(memberRepository.findAll()).willReturn(List.of(member));

        // when
        List<Member> allMembers = memberService.findAll();

        // then
        assertThat(allMembers).hasSize(1);
        assertThat(allMembers.get(0)).isEqualTo(member);
    }

    @Test
    @DisplayName("ID로 회원 조회")
    void findById() {
        // given
        given(memberRepository.findById(1L)).willReturn(Optional.of(member));

        // when
        Member foundMember = memberService.findById(1L);

        // then
        assertThat(foundMember).isEqualTo(member);
    }

    @Test
    @DisplayName("존재하지 않는 ID로 회원 조회할 때 예외 발생")
    void findByIdNonExistingId() {
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> memberService.findById(1L))
                .isInstanceOf(WakuWakuException.class)
                .extracting("status")
                .isEqualTo(ExceptionStatus.NONE_USER);
    }

    @Test
    @DisplayName("회원 정보 수정")
    void update() {
        // given
        MemberUpdateRequest updateRequest = new MemberUpdateRequest("newNickname", "newProfileUrl", "1991-01-01");

        given(memberRepository.findById(1L)).willReturn(Optional.of(member));

        given(memberRepository.save(any(Member.class)))
                .willAnswer(invocation -> {
                    Member updatedMember = invocation.getArgument(0);
                    updatedMember.setId(1L);
                    return updatedMember;
                });

        // when
        Long updatedMemberId = memberService.update(1L, updateRequest);

        // then
        assertThat(updatedMemberId).isEqualTo(1L);
        assertThat(member.getNickname()).isEqualTo(updateRequest.getNickname());
        assertThat(member.getProfileImageUrl()).isEqualTo(updateRequest.getProfileImageUrl());
        assertThat(member.getBirthday()).isEqualTo(updateRequest.getBirthday());
        then(memberRepository).should().findById(1L);
        then(memberRepository).should().save(any(Member.class));
    }



    @Test
    @DisplayName("회원 정보 수정 - 존재하지 않는 회원")
    void updateNonExistingMember() {
        // given
        MemberUpdateRequest updateRequest = new MemberUpdateRequest("newNickname", "newProfileUrl", "1991-01-01");
        given(memberRepository.findById(1L)).willReturn(Optional.empty());

        // when / then
        assertThatThrownBy(() -> memberService.update(1L, updateRequest))
                .isInstanceOf(WakuWakuException.class)
                .hasFieldOrPropertyWithValue("status", ExceptionStatus.NONE_USER);

        then(memberRepository).should().findById(1L);
        then(memberRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("존재하지 않는 ID로 회원 정보 수정할 때 예외 발생")
    void updateNonExistingId() {
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.empty());
        MemberUpdateRequest updateRequest = new MemberUpdateRequest("newNickname", "newProfileUrl", "1991-01-01");

        // when, then
        assertThatThrownBy(() -> memberService.findById(1L))
                .isInstanceOf(WakuWakuException.class)
                .extracting("status")
                .isEqualTo(ExceptionStatus.NONE_USER);
        then(memberRepository).should().findById(anyLong());
        then(memberRepository).should(never()).save(any());
    }

    @Test
    @DisplayName("회원 비활성화")
    void deactivateById() {
        // given
        Long memberId = 1L;
        Member member = new Member();
        member.setId(memberId);
        member.setCheckStatus("Y");

        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

        // when
        memberService.deactivateById(memberId);

        // then
        then(memberRepository).should().findById(memberId);
        Assertions.assertEquals("N", member.getCheckStatus());
        then(memberRepository).should().save(member);
    }

}
