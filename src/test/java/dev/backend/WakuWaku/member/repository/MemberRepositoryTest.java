package dev.backend.wakuwaku.member.repository;

import dev.backend.wakuwaku.domain.member.entity.MemberEntity;
import dev.backend.wakuwaku.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testFindByMemberId_ExistingMemberId() {
        // Given
        String memberId = "test1";

        // When
        Optional<MemberEntity> optionalMember = memberRepository.findByMemberId(memberId);

        // Then
        assertTrue(optionalMember.isPresent()); // 회원 정보가 존재해야 함
        MemberEntity member = optionalMember.get();
        assertEquals(memberId, member.getMemberId()); // 회원 아이디가 일치해야 함
        // 그 외 필요한 필드들을 확인할 수 있음
    }

    @Test
    public void testFindByMemberId_NonExistingMemberId() {
        // Given
        String memberId = "NotExistingId";

        // When
        Optional<MemberEntity> optionalMember = memberRepository.findByMemberId(memberId);

        // Then
        assertFalse(optionalMember.isPresent()); // 비어 있는 Optional이 반환되어야 함
    }
}