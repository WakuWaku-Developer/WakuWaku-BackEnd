package dev.backend.wakuwaku.domain.like.dto.request;

import dev.backend.wakuwaku.domain.like.entity.Like;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikePushRequest {

    private Long memberId;

    private Long restaurantId;


    public LikePushRequest(Like likes){
        this.memberId = likes.getMemberId().getId();
        this.restaurantId = likes.getRestaurantId().getId();
    }
}
