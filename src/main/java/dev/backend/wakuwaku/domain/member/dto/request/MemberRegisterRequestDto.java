package dev.backend.wakuwaku.domain.member.dto.request;

import dev.backend.wakuwaku.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRegisterRequestDto {

    private String memberId;
    private String memberPassword;
    private String memeberEmail;
    private String memberName;

    public MemberRegisterRequestDto(String memberId, String memberPassword, String memeberEmail, String memberName){
        this.memberId = memberId;
        this.memberPassword = memberPassword;
        this.memeberEmail = memeberEmail;
        this.memberName = memberName;
    }

    public Member toMemberEntity(){
        return Member.builder()
                .memberId(this.memberId)
                .memberPassword(this.memberPassword)
                .memberEmail(this.memeberEmail)
                .memberName(this.memberName)
                .build();
    }

    public void setMemberId(String memberId) {this.memberId = memberId; }

    public void setMemberPassword(String memberPassword) {this.memberPassword = memberPassword; }
}
