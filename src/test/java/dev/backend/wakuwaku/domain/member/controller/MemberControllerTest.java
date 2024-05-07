package dev.backend.wakuwaku.domain.member.controller;

import dev.backend.wakuwaku.domain.member.dto.request.MemberLoginRequest;
import dev.backend.wakuwaku.domain.member.dto.request.MemberRegisterRequest;
import dev.backend.wakuwaku.domain.member.dto.request.MemberUpdateRequest;
import dev.backend.wakuwaku.domain.member.dto.response.GetMemberResponse;
import dev.backend.wakuwaku.domain.member.dto.response.MemberIdResponse;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    @Test
    @DisplayName("회원 가입 테스트")
    void register() {
        // Given
        MemberRegisterRequest registerRequest = new MemberRegisterRequest();
        when(memberService.register(registerRequest)).thenReturn(1L);

        // When
        ResponseEntity<MemberIdResponse> response = memberController.register(registerRequest);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    @DisplayName("회원 로그인 테스트")
    void login() {
        // Given
        MemberLoginRequest loginRequest = new MemberLoginRequest();
        when(memberService.login(loginRequest)).thenReturn(1L);

        // When
        ResponseEntity<MemberIdResponse> response = memberController.login(loginRequest);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    @DisplayName("회원 조회 테스트")
    void findById() {
        // Given
        Long memberId = 1L;
        Member member = new Member();
        when(memberService.findById(memberId)).thenReturn(member);

        // When
        ResponseEntity<GetMemberResponse> response = memberController.findById(memberId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(member.getId(), response.getBody().getId()); // 변경된 부분
    }

    @Test
    @DisplayName("회원 정보 업데이트 테스트")
    void update() {
        // Given
        Long memberId = 1L;
        MemberUpdateRequest updateRequest = new MemberUpdateRequest();
        when(memberService.update(memberId, updateRequest)).thenReturn(1L);

        // When
        ResponseEntity<MemberIdResponse> response = memberController.update(memberId, updateRequest);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void delete() {
        // Given
        Long memberId = 1L;

        // When
        ResponseEntity<Void> response = memberController.delete(memberId);

        // Then
        verify(memberService).deleteById(memberId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("회원 조회 테스트")
    void findAll() {
        // Given
        List<Member> members = new ArrayList<>();
        when(memberService.findAll()).thenReturn(members);

        // When
        ResponseEntity<List<GetMemberResponse>> response = memberController.findAll();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(members.size(), response.getBody().size());
    }
}
