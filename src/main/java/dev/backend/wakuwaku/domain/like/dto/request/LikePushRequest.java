package dev.backend.wakuwaku.domain.like.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikePushRequest {
    private Long memberId;
    private Long restaurantId;
}
