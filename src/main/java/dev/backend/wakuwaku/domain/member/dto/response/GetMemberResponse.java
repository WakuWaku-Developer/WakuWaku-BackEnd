package dev.backend.wakuwaku.domain.member.dto.response;

import dev.backend.wakuwaku.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetMemberResponse {

    private Long id;
    private String memberEmail;
    private String memberName;
    private String memberNickname;
    private String memberBirth;

    public GetMemberResponse(Member memberEntity) {
        this.id = memberEntity.getId();
        this.memberEmail = memberEntity.getMemberEmail();
        this.memberName = memberEntity.getMemberName();
        this.memberNickname = memberEntity.getMemberNickname();
        this.memberBirth = memberEntity.getMemberBirth();
    }

}
