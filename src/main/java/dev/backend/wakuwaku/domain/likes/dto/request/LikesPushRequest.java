package dev.backend.wakuwaku.domain.likes.dto.request;

import dev.backend.wakuwaku.domain.likes.entity.LikesEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikesPushRequest {

    private Long memberId;

    private Long restaurantId;


    public LikesPushRequest(LikesEntity likesEntity){
        this.memberId = likesEntity.getMemberId().getId();
        this.restaurantId = likesEntity.getRestaurantId().getId();
    }
}
