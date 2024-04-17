package dev.backend.wakuwaku.domain.member.dto.response;

import dev.backend.wakuwaku.domain.member.entity.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetMemberResponse {
    private String memberId;
    private String memberEmail;
    private String memberName;
    public GetMemberResponse(MemberEntity memberEntity) {
        this.memberId = memberEntity.getMemberId();
        this.memberEmail = memberEntity.getMemberEmail();
        this.memberName = memberEntity.getMemberName();
    }
}
