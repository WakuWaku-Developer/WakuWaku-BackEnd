package dev.backend.wakuwaku.domain.like.dto.response;

import dev.backend.wakuwaku.domain.like.entity.Like;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetLikeResponse {
    private Long memberId;
    private Long restaurantId;
    private String likesStatus;

    public GetLikeResponse(Like likes){
        this.memberId = likes.getMemberId().getId();
        this.restaurantId = likes.getRestaurantId().getId();
        this.likesStatus = likes.getLikeStatus();
    }

}
