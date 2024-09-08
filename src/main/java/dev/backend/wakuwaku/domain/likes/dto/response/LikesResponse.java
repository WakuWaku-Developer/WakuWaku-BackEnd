package dev.backend.wakuwaku.domain.likes.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikesResponse {
    private Long likeId;

    public LikesResponse(Long likeId) {
        this.likeId = likeId;
    }

}
