package dev.backend.wakuwaku.domain.member.dto.request;

import dev.backend.wakuwaku.domain.member.entity.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRegisterRequest {
    private String memberId;
    private String memberPassword;
    private String memberEmail;
    private String memberName;

    public MemberRegisterRequest(String memberId, String memberPassword, String memberEmail, String memberName) {
        this.memberId = memberId;
        this.memberPassword = memberPassword;
        this.memberEmail = memberEmail;
        this.memberName = memberName;
    }

    public MemberEntity toMemberEntity() {
        return MemberEntity.builder()
                .memberName(this.memberName)
                .memberId(this.memberId)
                .memberPassword(this.memberPassword)
                .memberEmail(this.memberEmail)
                .build();
    }


    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }
}
