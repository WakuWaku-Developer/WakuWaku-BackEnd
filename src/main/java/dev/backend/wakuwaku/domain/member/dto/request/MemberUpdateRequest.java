package dev.backend.wakuwaku.domain.member.dto.request;

import dev.backend.wakuwaku.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateRequest {
    private String memberPassword;
    private String memberName;

    public MemberUpdateRequest(String memberPassword, String memberName)
    {
        this.memberPassword = memberPassword;
        this.memberName = memberName;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Member toMemberEntity() {
        return Member.builder()
                .memberName(this.memberName)
                .memberPassword(this.memberPassword)
                .build();
    }
}
