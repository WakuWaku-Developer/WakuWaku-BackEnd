package dev.backend.wakuwaku.domain.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@Setter
@NoArgsConstructor
public class MemberLoginRequest {
    private String memberEmail;

    private String memberPassword;

    public MemberLoginRequest(String memberEmail, String memberPassword){
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
    }
}
