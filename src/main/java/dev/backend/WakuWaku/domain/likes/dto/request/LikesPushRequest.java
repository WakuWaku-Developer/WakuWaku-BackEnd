package dev.backend.wakuwaku.domain.likes.dto.request;

import dev.backend.wakuwaku.domain.likes.entity.Likes;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikesPushRequest {

    private Long memberId;

    private Long restaurantId;


    public LikesPushRequest(Likes likes){
        this.memberId = likes.getMemberId().getId();
        this.restaurantId = likes.getRestaurantId().getId();
    }
}
