package dev.backend.wakuwaku.domain.member.repository;

import dev.backend.wakuwaku.domain.member.entity.MemberEntity;

import java.util.Optional;

public interface SpringDataJpaMemberRepository {
    Optional<MemberEntity> findByMemberEntity(String memberId);
}
