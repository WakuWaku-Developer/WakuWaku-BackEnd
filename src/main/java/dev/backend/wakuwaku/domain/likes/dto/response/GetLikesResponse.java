package dev.backend.wakuwaku.domain.likes.dto.response;

import dev.backend.wakuwaku.domain.likes.dto.LikesStatusType;
import dev.backend.wakuwaku.domain.likes.entity.Likes;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetLikesResponse {
    private Member memberId;
    private Restaurant restaurantId;
    private LikesStatusType likesStatus;

    public GetLikesResponse(Likes likes){
        this.memberId = likes.getMember();
        this.restaurantId = likes.getRestaurant();
        this.likesStatus = likes.getLikesStatus();
    }

}
