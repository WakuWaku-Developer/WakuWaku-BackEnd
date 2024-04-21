package dev.backend.wakuwaku.domain.member.dto.response;

import dev.backend.wakuwaku.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetMemberResponseDto {

    private String memberId;
    private String memberEmail;
    private String memberName;

    public GetMemberResponseDto(Member memberEntity) {
        this.memberId = memberEntity.getMemberId();
        this.memberEmail = memberEntity.getMemberEmail();
        this.memberName = memberEntity.getMemberName();
    }

}
