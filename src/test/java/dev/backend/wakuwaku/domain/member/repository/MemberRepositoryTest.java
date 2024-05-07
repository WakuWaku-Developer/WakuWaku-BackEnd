package dev.backend.wakuwaku.domain.member.repository;

import dev.backend.wakuwaku.domain.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 저장 테스트")
    void saveMember() {
        // Given
        Member member = createMemberEntity("test@example.com");

        // When
        Member savedMember = memberRepository.save(member);

        // Then
        assertNotNull(savedMember.getId());
    }

    @Test
    @DisplayName("이메일로 회원 조회 테스트")
    void getMemberByMemberId() {
        // Given
        Member savedMember = saveMember("test@example.com");

        // When
        Optional<Member> optionalMember = memberRepository.findByMemberEmail("test@example.com");

        // Then
        assertTrue(optionalMember.isPresent());
        assertEquals(savedMember.getMemberEmail(), optionalMember.get().getMemberEmail());
    }

    @Test
    @DisplayName("모든 회원 조회 테스트")
    void getAllMembers() {
        // Given
        saveMember("test1@example.com");
        saveMember("test2@example.com");

        // When
        List<Member> members = memberRepository.findAll();

        // Then
        assertEquals(2, members.size());
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteMember() {
        // Given
        Member savedMember = saveMember("test@example.com");

        // When
        memberRepository.deleteById(savedMember.getId());

        // Then
        assertTrue(memberRepository.findByMemberEmail("test@example.com").isEmpty());
    }

    private Member saveMember(String email) {
        Member member = createMemberEntity(email);
        return memberRepository.save(member);
    }

    private Member createMemberEntity(String email) {
        Member member = new Member();
        member.setMemberEmail(email);
        member.setMemberPassword("password"); // Set memberPassword
        // Set other fields as needed
        return member;
    }
}
