package dev.backend.wakuwaku.domain.likes.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PushLikesRequest {
    private Long memberId;

    private LikesRestaurantRequest restaurantInfo;

    @Builder
    public PushLikesRequest(Long memberId, LikesRestaurantRequest restaurantInfo) {
        this.memberId = memberId;
        this.restaurantInfo = restaurantInfo;
    }
}
