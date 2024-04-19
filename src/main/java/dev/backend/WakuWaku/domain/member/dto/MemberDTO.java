package dev.backend.wakuwaku.domain.member.dto;

import dev.backend.wakuwaku.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDto {
    private String memberId;
    private String memberName;
    private String memberEmail;

    public Member toMemberEntity() {
        return Member.builder()
                .memberId(memberId)
                .memberEmail(memberEmail)
                .memberName(memberName)
                .build();
    }
}
