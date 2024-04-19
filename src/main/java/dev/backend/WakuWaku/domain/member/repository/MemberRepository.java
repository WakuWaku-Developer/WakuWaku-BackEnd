package dev.backend.wakuwaku.domain.member.repository;

import dev.backend.wakuwaku.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


// 두번째 인자는 PK의 타입을 명시 (Long)
public interface MemberRepository extends JpaRepository<Member, Long>{
    // 아이디로 회원 정보 조회
    // (select * from member_table where member_id =?)
    // 모든 레파지토리에서 주고받는 객체 = Entity
    Optional<Member> findByMemberId(String memberId);


}