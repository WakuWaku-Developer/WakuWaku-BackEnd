package dev.backend.wakuwaku.domain.likes.dto.response;

import dev.backend.wakuwaku.domain.likes.entity.LikesEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetLikesResponse {
    private Long memberId;
    private Long restaurantId;
    private String likesStatus;

    public GetLikesResponse(LikesEntity likesEntity){
        this.memberId = likesEntity.getMemberId().getId();
        this.restaurantId = likesEntity.getRestaurantId().getId();
        this.likesStatus = likesEntity.getLikesStatus();
    }

}
