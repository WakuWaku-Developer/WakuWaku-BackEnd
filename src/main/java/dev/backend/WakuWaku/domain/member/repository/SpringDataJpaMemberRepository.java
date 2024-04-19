package dev.backend.wakuwaku.domain.member.repository;

import dev.backend.wakuwaku.domain.member.entity.Member;

import java.util.Optional;

public interface SpringDataJpaMemberRepository {
    Optional<Member> findByMemberEntity(String memberId);
}
