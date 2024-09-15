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

    private String profileUrl;

    private String nickname;

    private String accessToken;

    private List<String> likedRestaurantPlaceIds = new ArrayList<>();

    @Builder
    public OAuthLoginResponse(Long id, String profileUrl, String nickname, String accessToken, List<String> likedRestaurantPlaceIds) {
        this.id = id;
        this.profileUrl = profileUrl;
        this.nickname = nickname;
        this.accessToken = accessToken;
        this.likedRestaurantPlaceIds = likedRestaurantPlaceIds;
    }
}
