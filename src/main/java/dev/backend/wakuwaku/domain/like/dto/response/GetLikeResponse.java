package dev.backend.wakuwaku.domain.like.dto.response;

import dev.backend.wakuwaku.domain.like.entity.Like;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetLikeResponse {
    private Member memberId;
    private Restaurant restaurantId;
    private String likesStatus;

    public GetLikeResponse(Like likes){
        this.memberId = likes.getMember();
        this.restaurantId = likes.getRestaurant();
        this.likesStatus = likes.getLikeStatus();
    }

}
