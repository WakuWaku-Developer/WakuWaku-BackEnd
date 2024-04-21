package dev.backend.wakuwaku.domain.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginRequest {
    private String memberId;

    private String memberPassword;

    public MemberLoginRequest(String memberId, String memberPassword){
        this.memberId = memberId;
        this.memberPassword = memberPassword;
    }
}
