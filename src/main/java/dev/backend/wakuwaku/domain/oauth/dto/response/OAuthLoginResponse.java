package dev.backend.wakuwaku.domain.oauth.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class OAuthLoginResponse {
    private Long id;

    private List<String> likedRestaurantPlaceIds = new ArrayList<>();

    @Builder
    public OAuthLoginResponse(Long id, List<String> likedRestaurantPlaceIds) {
        this.id = id;
        this.likedRestaurantPlaceIds = likedRestaurantPlaceIds;
    }
}
