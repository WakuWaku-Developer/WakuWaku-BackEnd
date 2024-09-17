package dev.backend.wakuwaku.domain.likes.dto.response;

import dev.backend.wakuwaku.domain.likes.entity.Likes;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class AllLikesResponse {
    private List<GetLikesResponse> likesRestaurants = new ArrayList<>();

    private int totalPages;

    @Builder
    public AllLikesResponse(List<Likes> likesList, int totalPages) {
        this.likesRestaurants = likesList.stream()
                                         .map(GetLikesResponse::new)
                                         .toList();
        this.totalPages = totalPages;
    }
}
