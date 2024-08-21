package dev.backend.wakuwaku.domain.like.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikePushRequest {

    private Long memberId;

    private String restaurantPlaceId;

}
