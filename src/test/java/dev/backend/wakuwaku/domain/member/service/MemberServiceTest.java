package dev.backend.wakuwaku.domain.member.service;

import dev.backend.wakuwaku.domain.member.dto.request.MemberUpdateRequest;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
public class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private MemberService memberService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("회원 가입 서비스")
    void testRegister() {
        // 테스트에 필요한 MemberEntity 생성
        Member memberEntity = new Member();
        memberEntity.setMemberId("testMember");
        // 중복 회원이 없는 상황 가정
        when(memberRepository.findByMemberId(anyString())).thenReturn(Optional.empty());
        // 저장된 MemberEntity의 아이디를 반환하도록 설정
        when(memberRepository.save(any())).thenAnswer(invocation -> {
            Member savedMember = invocation.getArgument(0);
            savedMember.setId(1L); // 임의의 아이디 설정
            return savedMember;
        });
        // register 메서드 호출 및 반환 값 받기
        Long memberId = memberService.register(memberEntity);
        // 반환된 memberId가 null이 아닌지 확인
        assertNotNull(memberId);
        // memberRepository의 메서드가 호출되었는지 확인
        verify(memberRepository, times(1)).findByMemberId(anyString());
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("회원 로그인 서비스")
    void testLogin() {
        String memberId = "testMember";
        String password = "password";

        Member member = new Member();
        member.setMemberId(memberId);
        member.setMemberPassword(password);
        member.setId(1L);

        when(memberRepository.findByMemberId(anyString())).thenReturn(Optional.of(member));

        Long foundId = memberService.login(memberId, password);

        assertNotNull(foundId);
        verify(memberRepository, times(1)).findByMemberId(anyString());
    }
    @Test
    @DisplayName("회원 가입 서비스")
    void testFindAll() {
        List<Member> memberList = new ArrayList<>();

        when(memberRepository.findAll()).thenReturn(memberList);

        List<Member> foundMembers = memberService.findAll();

        assertNotNull(foundMembers);
        assertEquals(memberList, foundMembers);

        verify(memberRepository, times(1)).findAll();
    }
    @Test
    @DisplayName("회원 검색 서비스")
    void testFindById() {
        Long id = 1L;

        Member memberEntity = new Member();

        memberEntity.setId(id);

        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(memberEntity));

        Member foundMember = memberService.findById(id);
        assertNotNull(foundMember);
        assertEquals(id, foundMember.getId());

        verify(memberRepository, times(1)).findById(anyLong());
    }
    @Test
    @DisplayName("회원 정보 수정 서비스")
    void testUpdate() {
        Long id = 1L;
        MemberUpdateRequest updateRequest = new MemberUpdateRequest();

        updateRequest.setMemberPassword("newPassword");
        updateRequest.setMemberName("New Name");

        Member memberEntity = new Member();

        memberEntity.setId(id);
        memberEntity.setMemberPassword("oldPassword");
        memberEntity.setMemberName("Old Name");

        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(memberEntity));
        when(memberRepository.save(any())).thenReturn(memberEntity);

        Long updatedId = memberService.update(id, updateRequest);

        assertNotNull(updatedId);

        assertEquals(id, updatedId);
        assertEquals(updateRequest.getMemberPassword(), memberEntity.getMemberPassword());
        assertEquals(updateRequest.getMemberName(), memberEntity.getMemberName());

        verify(memberRepository, times(1)).findById(anyLong());
        verify(memberRepository, times(1)).save(any(Member.class));
    }
    @Test
    @DisplayName("회원 탈퇴 서비스")
    void testDeleteById() {
        Long id = 1L;
        memberService.deleteById(id);
        verify(memberRepository, times(1)).deleteById(anyLong());
    }
}
