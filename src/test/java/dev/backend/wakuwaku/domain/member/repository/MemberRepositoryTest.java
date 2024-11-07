package dev.backend.wakuwaku.domain.member.repository;

import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import dev.backend.wakuwaku.domain.oauth.dto.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    private Member savedMember;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                              .oauthServerId("testOauthServerId")
                              .oauthServerType(OauthServerType.GOOGLE)
                              .email("test@example.com")
                              .birthday("1990-01-01")
                              .nickname("testNickname")
                              .profileImageUrl("http://example.com/image.jpg")
                              .role(Role.USER)
                              .build();

        savedMember = memberRepository.save(member);
    }

    @DisplayName("ID로 Member 조회")
    @Test
    void findById() {
        // given
        Long memberId = savedMember.getId();

        // when
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        // then
        assertThat(optionalMember).isPresent();

        Member foundMember = optionalMember.get();

        assertThat(foundMember.getId()).isEqualTo(savedMember.getId());
        assertThat(foundMember.getEmail()).isEqualTo(savedMember.getEmail());
        assertThat(foundMember.getNickname()).isEqualTo(savedMember.getNickname());
        assertThat(foundMember.getProfileImageUrl()).isEqualTo(savedMember.getProfileImageUrl());
        assertThat(foundMember.getBirthday()).isEqualTo(savedMember.getBirthday());
        assertThat(foundMember.getOauthServerId()).isEqualTo(savedMember.getOauthServerId());
        assertThat(foundMember.getOauthServerType()).isEqualTo(savedMember.getOauthServerType());
        assertThat(foundMember.getRole()).isEqualTo(savedMember.getRole());
    }

    @DisplayName("이메일로 Member 조회")
    @Test
    void findByEmail() {
        // given
        String email = savedMember.getEmail();

        // when
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        // then
        assertThat(optionalMember).isPresent();

        Member foundMember = optionalMember.get();

        assertThat(foundMember.getId()).isEqualTo(savedMember.getId());
        assertThat(foundMember.getEmail()).isEqualTo(savedMember.getEmail());
        assertThat(foundMember.getNickname()).isEqualTo(savedMember.getNickname());
        assertThat(foundMember.getProfileImageUrl()).isEqualTo(savedMember.getProfileImageUrl());
        assertThat(foundMember.getBirthday()).isEqualTo(savedMember.getBirthday());
        assertThat(foundMember.getOauthServerId()).isEqualTo(savedMember.getOauthServerId());
        assertThat(foundMember.getOauthServerType()).isEqualTo(savedMember.getOauthServerType());
        assertThat(foundMember.getRole()).isEqualTo(savedMember.getRole());
    }

    @DisplayName("ACTIVE 멤버 전체 조회")
    @Test
    void findAllByActiveMember() {
        // given
        Member member1 = createMember(1);
        memberRepository.save(member1);

        Member member2 = createMember(2);
        memberRepository.save(member2);

        Member member3 = createMember(3);
        memberRepository.save(member3);

        Member member4 = createMember(4);
        memberRepository.save(member4);

        Member member5 = createMember(5);
        memberRepository.save(member5);

        member5.deactivate();

        // when
        List<Member> allByActiveMember = memberRepository.findAllByActiveMember();

        // then
        assertThat(allByActiveMember).hasSize(5);
    }

    private Member createMember(int number) {
        return Member.builder()
                .oauthServerId("testOauthServerId" + number)
                .oauthServerType(OauthServerType.NAVER)
                .email("test" + number + "@example.com")
                .birthday("199" + number + "-0" + number + "-0" + number)
                .nickname("testNickname" + number)
                .profileImageUrl("http://example.com/image" + number + ".jpg")
                .role(Role.USER)
                .build();
    }
}
