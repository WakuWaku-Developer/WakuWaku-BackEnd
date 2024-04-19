package dev.backend.wakuwaku.domain.likes.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikesRequestDto {
    private Long memberId;
    private Long restaurantId;

    public LikesRequestDto(Long memberId, Long restaurantId) {
        this.memberId = memberId;
        this.restaurantId = restaurantId;
    }
}

