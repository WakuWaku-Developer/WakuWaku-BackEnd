package dev.backend.wakuwaku.domain.member.service;

import dev.backend.wakuwaku.domain.member.dto.request.MemberLoginRequest;
import dev.backend.wakuwaku.domain.member.dto.request.MemberRegisterRequest;
import dev.backend.wakuwaku.domain.member.dto.request.MemberUpdateRequest;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원 등록 테스트")
    void register() {
        // Given
        MemberRegisterRequest registerRequest = new MemberRegisterRequest();
        registerRequest.setMemberEmail("test@example.com");
        registerRequest.setMemberPassword("password");
        registerRequest.setMemberName("Test User");
        registerRequest.setMemberNickname("testuser");
        registerRequest.setMemberBirth("2000-01-01");

        // When
        Long id = memberService.register(registerRequest);

        // Then
        //assertNotNull(id); // 반환된 id가 null이 아닌지 확인
    }

    @Test
    @DisplayName("회원 로그인 테스트")
    void login() {
        // Given
        MemberLoginRequest loginRequest = new MemberLoginRequest("test@example.com", "password");
        Member testMember = new Member();
        testMember.setId(1L);
        testMember.setMemberEmail("test@example.com");
        testMember.setMemberPassword("password");
        when(memberRepository.findByMemberEmail("test@example.com")).thenReturn(java.util.Optional.of(testMember));

        // When
        Long memberId = memberService.login(loginRequest);

        // Then
        assertEquals(1L, memberId);
    }

    @Test
    @DisplayName("회원 조회 테스트")
    void findById() {
        // Given
        Long memberId = 1L;
        Member testMember = new Member();
        testMember.setId(memberId);
        when(memberRepository.findById(memberId)).thenReturn(java.util.Optional.of(testMember));

        // When
        Member foundMember = memberService.findById(memberId);

        // Then
        assertEquals(memberId, foundMember.getId());
    }

    @Test
    @DisplayName("회원 정보 수정 테스트")
    void update() {
        // Given
        Long memberId = 1L;
        MemberUpdateRequest updateRequest = new MemberUpdateRequest();
        updateRequest.setMemberPassword("newpassword");
        updateRequest.setMemberName("Updated Name");
        updateRequest.setMemberNickname("updatednickname");
        updateRequest.setMemberBirth("2000-01-01");

        Member existingMember = new Member();
        existingMember.setId(memberId);
        existingMember.setMemberPassword("password");
        when(memberRepository.findById(memberId)).thenReturn(java.util.Optional.of(existingMember));

        // When
        Long updatedId = memberService.update(memberId, updateRequest);

        // Then
        assertEquals(memberId, updatedId);
        assertEquals("newpassword", existingMember.getMemberPassword());
        assertEquals("Updated Name", existingMember.getMemberName());
        assertEquals("updatednickname", existingMember.getMemberNickname());
        assertEquals("2000-01-01", existingMember.getMemberBirth());
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteById() {
        // Given
        Long memberId = 1L;

        // When
        memberService.deleteById(memberId);

        // Then
        verify(memberRepository, times(1)).deleteById(memberId);
    }

    @Test
    @DisplayName("모든 회원 조회 테스트")
    void findAll() {
        // Given
        List<Member> members = new ArrayList<>();
        when(memberRepository.findAll()).thenReturn(members);

        // When
        List<Member> foundMembers = memberService.findAll();

        // Then
        assertEquals(members, foundMembers);
    }
}
