package dev.backend.wakuwaku.domain.member.repository;

import dev.backend.wakuwaku.domain.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원 정보를 저장합니다.")
    @Test
    void saveMember() {
        // given
        Member member1 = createMemberEntity(1);

        // when
        member1 = memberRepository.save(member1);

        // then
        Member result = memberRepository.findById(member1.getId()).orElse(null);
        assertThat(result).isNotNull();
        assertThat(member1).isEqualTo(result);
    }

    @DisplayName("해당 아이디와 일치하는 회원을 조회합니다.")
    @Test
    void getMemberByMemberId() {
        // given
        Member member1 = createMemberEntity(1);
        member1 = memberRepository.save(member1);

        // when
        Optional<Member> result = memberRepository.findByMemberId(member1.getMemberId());

        // then
        assertThat(result).isPresent();
        assertThat(member1).isEqualTo(result.get());
    }

    @DisplayName("전체 회원을 조회합니다.")
    @Test
    void getAllMembers() {
        // given
        Member member1 = createMemberEntity(1);
        Member member2 = createMemberEntity(2);

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> members = memberRepository.findAll();

        // then
        assertThat(members).hasSize(2);
    }

    @DisplayName("회원을 삭제합니다.")
    @Test
    void deleteMember() {
        // given
        Member member1 = createMemberEntity(1);
        member1 = memberRepository.save(member1);

        Member member2 = createMemberEntity(2);
        memberRepository.save(member2);

        // when
        memberRepository.deleteById(member1.getId());

        // then
        assertThat(memberRepository.findById(member1.getId())).isEmpty();
        assertThat(memberRepository.findAll()).hasSize(1);
    }

    private Member createMemberEntity(int number) {
        return Member.builder()
                .memberId("testId" + number)
                .memberEmail("testEmail" + number + "@test.com")
                .build();
    }
}
