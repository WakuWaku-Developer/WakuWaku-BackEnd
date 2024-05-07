package dev.backend.wakuwaku.domain.member.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberIdResponse {
    private Long id;

    public MemberIdResponse(Long id) {
        this.id = id;
    }
}

