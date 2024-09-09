package dev.backend.wakuwaku.domain.likes.dto.response;

import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class AllLikesResponse {
    private List<GetRestaurantResponse> likesRestaurants = new ArrayList<>();

    private int totalPages;

    @Builder
    public AllLikesResponse(List<Restaurant> likesRestaurants, int totalPages) {
        this.likesRestaurants = likesRestaurants.stream()
                                                .map(GetRestaurantResponse::new)
                                                .toList();
        this.totalPages = totalPages;
    }
}
