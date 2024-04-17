package dev.backend.wakuwaku.domain.member.dto;

import dev.backend.wakuwaku.domain.member.entity.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDTO {
    private String memberId;
    private String memberName;
    private String memberEmail;
    public MemberEntity toMemberEntity() {
        return MemberEntity.builder()
                .memberId(memberId)
                .memberEmail(memberEmail)
                .memberName(memberName)
                .build();
    }
}
