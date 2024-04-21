package dev.backend.wakuwaku.domain.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginRequestDto {
    private String memberId;

    private String memberPassword;

    public MemberLoginRequestDto(String memberId, String memberPassword){
        this.memberId = memberId;
        this.memberPassword = memberPassword;
    }
}
