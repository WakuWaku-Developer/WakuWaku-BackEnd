package dev.backend.wakuwaku.domain.likes.dto.response;

import dev.backend.wakuwaku.domain.likes.entity.Likes;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetLikesResponse {
    private Long memberId;
    private Long restaurantId;
    private String likesStatus;

    public GetLikesResponse(Likes likes){
        this.memberId = likes.getMemberId().getId();
        this.restaurantId = likes.getRestaurantId().getId();
        this.likesStatus = likes.getLikesStatus();
    }

}
