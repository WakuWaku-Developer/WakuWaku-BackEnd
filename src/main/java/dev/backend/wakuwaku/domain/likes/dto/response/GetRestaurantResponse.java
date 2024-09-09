package dev.backend.wakuwaku.domain.likes.dto.response;

import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetRestaurantResponse {
    private String placeId;

    private String name;

    private double lat;

    private double lng;

    private String photo;

    private int userRatingsTotal;

    private double rating;

    public GetRestaurantResponse(Restaurant restaurant) {
        this.placeId = restaurant.getPlaceId();
        this.name = restaurant.getName();
        this.lat = restaurant.getLat();
        this.lng = restaurant.getLng();
        this.photo = restaurant.getPhoto();
        this.userRatingsTotal = restaurant.getUserRatingsTotal();
        this.rating = restaurant.getRating();
    }
}
