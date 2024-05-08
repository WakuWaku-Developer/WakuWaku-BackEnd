
package dev.backend.wakuwaku.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.backend.wakuwaku.domain.member.dto.request.MemberRegisterRequest;
import dev.backend.wakuwaku.domain.member.dto.request.MemberUpdateRequest;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String NAME_SPACE = "/wakuwaku/v1/members";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    @DisplayName("회원 가입 테스트")
    void register() throws Exception {
        // Given
        MemberRegisterRequest registerRequest = new MemberRegisterRequest();
        when(memberService.register(any(MemberRegisterRequest.class))).thenReturn(1L);

        // When/Then
        mockMvc.perform(post(NAME_SPACE +"/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("회원 조회 테스트")
    void findById() throws Exception {
        // Given
        Long memberId = 1L;
        Member member = new Member();
        member.setId(memberId);
        when(memberService.findById(anyLong())).thenReturn(member);

        // When/Then
        mockMvc.perform(get(NAME_SPACE+"/{id}", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(memberId));
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void delete() throws Exception {
        // Given
        Long memberId = 1L;

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/wakuwaku/v1/members/{id}", memberId))
                .andExpect(status().isOk());

        // Verify
        verify(memberService).deleteById(memberId);
    }


    @Test
    @DisplayName("회원 정보 업데이트 테스트")
    void update() throws Exception {
        // Given
        Long memberId = 1L;
        MemberUpdateRequest updateRequest = new MemberUpdateRequest();
        when(memberService.update(eq(memberId), any(MemberUpdateRequest.class))).thenReturn(1L);

        // When/Then
        mockMvc.perform(put(NAME_SPACE+"/{id}", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("회원 목록 조회 테스트")
    void findAll() throws Exception {
        // Given
        List<Member> members = new ArrayList<>();
        when(memberService.findAll()).thenReturn(members);

        // When/Then
        mockMvc.perform(get(NAME_SPACE+"/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
