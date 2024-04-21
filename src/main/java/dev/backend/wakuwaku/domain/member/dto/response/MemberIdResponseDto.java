package dev.backend.wakuwaku.domain.member.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberIdResponseDto {
    private Long id;

    public MemberIdResponseDto(Long id) {
        this.id = id;
    }
}
