package dev.backend.wakuwaku.domain.like.dto.request;

import dev.backend.wakuwaku.domain.like.entity.Like;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikePushRequest {

    private Member member;

    private Restaurant restaurant;


    public LikePushRequest(Like like){
        this.member = like.getMember();
        this.restaurant = like.getRestaurant();
    }
}
