package dev.backend.wakuwaku.domain.member.service;

import dev.backend.wakuwaku.domain.member.dto.request.MemberUpdateRequest;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    /*
    회원가입
     */
    /*
    public Long register(Member memberEntity) {
        // 중복 검사 로직
        validateDuplicateMember(memberEntity);
        memberRepository.save(memberEntity);

        return memberEntity.getId();
    }


     */

    private void validateDuplicateMember(Member member) {
        memberRepository.findByEmail
                        (member.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException();
                });
    }


    /*
    회원 리스트
     */
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    /*
    회원 ID 찾기
     */
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalStateException()
                );
    }

    /*
    회원 정보 수정
     */
    public Long update(Long id, MemberUpdateRequest memberUpdateRequest) {
        Member member = memberRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalStateException()
                );

        member.setNickname(memberUpdateRequest.getNickname());
        member.setProfileImageUrl(memberUpdateRequest.getProfileImageUrl());
        member.setBirthday(memberUpdateRequest.getBirthday());

        memberRepository.save(member);

        // id;
        return member.getId();
    }

    /*
    회원 탈퇴
     */
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
}
