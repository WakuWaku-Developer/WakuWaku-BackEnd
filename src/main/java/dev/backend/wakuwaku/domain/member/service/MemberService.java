package dev.backend.wakuwaku.domain.member.service;

import dev.backend.wakuwaku.domain.member.dto.request.MemberLoginRequest;
import dev.backend.wakuwaku.domain.member.dto.request.MemberRegisterRequest;
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

    public Long register(MemberRegisterRequest registerRequest) {
        Member memberEntity = registerRequest.toMemberEntity();
        validateDuplicateMember(memberEntity);
        memberRepository.save(memberEntity);
        return memberEntity.getId();
    }

    public Long login(MemberLoginRequest loginRequest) {
        Member memberEntity = memberRepository.findByMemberEmail(loginRequest.getMemberEmail())
                .orElseThrow(() -> new IllegalStateException("등록되지 않은 이메일입니다."));

        if (!memberEntity.getMemberPassword().equals(loginRequest.getMemberPassword())) {
            throw new IllegalStateException("비밀번호가 틀렸습니다.");
        }

        return memberEntity.getId();
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));
    }

    public Long update(Long id, MemberUpdateRequest updateRequest) {
        Member memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));

        if (updateRequest.getMemberPassword() != null) {
            memberEntity.setMemberPassword(updateRequest.getMemberPassword());
        }
        if (updateRequest.getMemberName() != null) {
            memberEntity.setMemberName(updateRequest.getMemberName());
        }
        if (updateRequest.getMemberNickname() != null) {
            memberEntity.setMemberNickname(updateRequest.getMemberNickname());
        }
        if (updateRequest.getMemberBirth() != null) {
            memberEntity.setMemberBirth(updateRequest.getMemberBirth());
        }

        memberRepository.save(memberEntity);
        return memberEntity.getId();
    }



    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByMemberEmail(member.getMemberEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 이메일입니다.");
                });
    }
}