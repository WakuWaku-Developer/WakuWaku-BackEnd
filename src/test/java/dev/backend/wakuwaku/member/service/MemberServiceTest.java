package dev.backend.wakuwaku.member.service;

import dev.backend.wakuwaku.domain.member.dto.request.MemberUpdateRequest;
import dev.backend.wakuwaku.domain.member.entity.MemberEntity;
import dev.backend.wakuwaku.domain.member.repository.MemberRepository;
import dev.backend.wakuwaku.domain.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
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

class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }



    @Test
    void testRegister() {
        // 테스트에 필요한 MemberEntity 생성
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberId("testMember");

        // 중복 회원이 없는 상황 가정
        when(memberRepository.findByMemberId(anyString())).thenReturn(Optional.empty());

        // 저장된 MemberEntity의 아이디를 반환하도록 설정
        when(memberRepository.save(any())).thenAnswer(invocation -> {
            MemberEntity savedMember = invocation.getArgument(0);
            savedMember.setId(1L); // 임의의 아이디 설정
            return savedMember;
        });

        // register 메서드 호출 및 반환 값 받기
        Long memberId = memberService.register(memberEntity);

        // 반환된 memberId가 null이 아닌지 확인
        assertNotNull(memberId);


        // memberRepository의 메서드가 호출되었는지 확인
        verify(memberRepository, times(1)).findByMemberId(anyString());
        verify(memberRepository, times(1)).save(any(MemberEntity.class));
    }
    @Test
    void testLogin() {
        String memberId = "testMember";
        String password = "password";
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberId(memberId);
        memberEntity.setMemberPassword(password);
        memberEntity.setId(1L);

        when(memberRepository.findByMemberId(anyString())).thenReturn(Optional.of(memberEntity));

        Long foundId = memberService.login(memberId, password);

        assertNotNull(foundId);
        verify(memberRepository, times(1)).findByMemberId(anyString());
    }

    @Test
    void testFindAll() {
        List<MemberEntity> memberList = new ArrayList<>();
        when(memberRepository.findAll()).thenReturn(memberList);

        List<MemberEntity> foundMembers = memberService.findAll();

        assertNotNull(foundMembers);
        assertEquals(memberList, foundMembers);
        verify(memberRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Long id = 1L;
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(id);
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(memberEntity));

        MemberEntity foundMember = memberService.findById(id);

        assertNotNull(foundMember);
        assertEquals(id, foundMember.getId());
        verify(memberRepository, times(1)).findById(anyLong());
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        MemberUpdateRequest updateRequest = new MemberUpdateRequest();
        updateRequest.setMemberPassword("newPassword");
        updateRequest.setMemberName("New Name");

        MemberEntity memberEntity = new MemberEntity();
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
        verify(memberRepository, times(1)).save(any(MemberEntity.class));
    }

    @Test
    void testDeleteById() {
        Long id = 1L;

        memberService.deleteById(id);

        verify(memberRepository, times(1)).deleteById(anyLong());
    }
}