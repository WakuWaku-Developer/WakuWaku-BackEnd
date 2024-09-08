package dev.backend.wakuwaku.domain.likes.dto.response;

import dev.backend.wakuwaku.domain.likes.entity.Likes;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikesResponse {
    private Long likeId;

    public LikesResponse(Likes likes) {
        this.likeId = likes.getId();
    }
}
