package dev.backend.wakuwaku.domain.likes.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikesRestaurantRequest {
    private String placeId;

    private String name;

    private double lat;

    private double lng;

    private String photo = "";

    private int userRatingsTotal;

    private double rating;

    @Builder
    public LikesRestaurantRequest(String placeId, String name, double lat, double lng, String photo, int userRatingsTotal, double rating) {
        this.placeId = placeId;
        this.name = name;
        this.lat = lat;
        this.lng = lng;

        if (photo != null && !photo.isBlank() && !photo.isEmpty()) {
            this.photo = photo;
        }

        this.userRatingsTotal = userRatingsTotal;
        this.rating = rating;
    }
}
