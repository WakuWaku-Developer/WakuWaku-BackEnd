package dev.backend.wakuwaku.domain.member.dto.request;

import dev.backend.wakuwaku.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateRequest {
    private String memberPassword;
    private String memberName;
    private String memberNickname;
    private String memberBirth;

    public MemberUpdateRequest(String memberPassword, String memberName, String memberNickname, String memberBirth)
    {
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.memberNickname = memberNickname;
        this.memberBirth = memberBirth;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }
    public void setMemberBirth(String memberBirth) {
        this.memberBirth = memberBirth;
    }


    @Builder
    public Member toMemberEntity() {
        return Member.builder()
                .memberName(this.memberName)
                .memberPassword(this.memberPassword)
                .memberNickname(this.memberNickname)
                .memberBirth(this.memberBirth)
                .build();
    }
}
