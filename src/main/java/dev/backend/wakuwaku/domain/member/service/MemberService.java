package dev.backend.wakuwaku.domain.member.service;

import dev.backend.wakuwaku.domain.member.dto.request.MemberUpdateRequest;
import dev.backend.wakuwaku.domain.member.entity.MemberEntity;
import dev.backend.wakuwaku.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /*
    회원가입
     */
    public Long register(MemberEntity memberEntity) {
        // 중복 검사 로직
        validateDuplicateMember(memberEntity);
        memberRepository.save(memberEntity);

        return memberEntity.getId();
    }

    private void validateDuplicateMember(MemberEntity member) {
        memberRepository.findByMemberId(member.getMemberId())
                .ifPresent(m -> {
                    throw new IllegalStateException();
                });
    }

    /*
    로그인
     */
    public Long login(String memberId, String password) {
        MemberEntity memberEntity = memberRepository.findByMemberId(memberId)
                .orElseThrow(
                        () -> new IllegalStateException()
                );

        if (!memberEntity.getMemberPassword().equals(password)) {
            throw new IllegalStateException();
        }

        return memberEntity.getId();
    }

    /*
    회원 리스트
     */
    public List<MemberEntity> findAll() {
        return memberRepository.findAll();
    }

    /*
    회원 ID 찾기
     */
    public MemberEntity findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalStateException()
                );
    }

    /*
    회원 정보 수정
     */
    public Long update(Long id, MemberUpdateRequest memberUpdateRequest) {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalStateException()
                );

        memberEntity.setMemberPassword(memberUpdateRequest.getMemberPassword());
        memberEntity.setMemberName(memberUpdateRequest.getMemberName());

        memberRepository.save(memberEntity);

        // id;
        return memberEntity.getId();
    }

    /*
    회원 탈퇴
     */
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }


}
