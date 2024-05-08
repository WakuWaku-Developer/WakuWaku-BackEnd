package dev.backend.wakuwaku.domain.member.dto.request;

import dev.backend.wakuwaku.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterRequest {

    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberNickname;
    private String memberBirth;

    public Member toMemberEntity(){
        return Member.builder()
                .memberEmail(this.memberEmail)
                .memberPassword(this.memberPassword)
                .memberName(this.memberName)
                .memberNickname(this.memberNickname)
                .memberBirth(this.memberBirth)
                .build();
    }
}