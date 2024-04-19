package dev.backend.wakuwaku.domain.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginRequest {
    private String memberId;
    private String password;

    public MemberLoginRequest(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }
}
