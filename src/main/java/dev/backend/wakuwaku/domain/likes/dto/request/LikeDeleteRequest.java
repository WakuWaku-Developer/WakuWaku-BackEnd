package dev.backend.wakuwaku.domain.likes.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeDeleteRequest {
    private Long memberId;
    private Long restaurantId;
}
