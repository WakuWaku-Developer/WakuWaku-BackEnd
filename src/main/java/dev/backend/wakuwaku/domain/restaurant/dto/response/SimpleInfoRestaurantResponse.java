package dev.backend.wakuwaku.domain.restaurant.dto.response;

import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import lombok.Data;

import java.util.List;

@Data
public class SimpleInfoRestaurantResponse {
    private final String placeId;
    private final String name;
    private final Number rating;
    private final Number userRatingsTotal;
    private final double lat;
    private final double lng;
    private final List<String> photoUrl;

    public SimpleInfoRestaurantResponse(Restaurant restaurant) {
        this.placeId = restaurant.getPlaceId();
        this.name = restaurant.getName();
        this.rating = restaurant.getRating();
        this.userRatingsTotal = restaurant.getUserRatingsTotal();
        this.lat = restaurant.getLat();
        this.lng = restaurant.getLng();
        this.photoUrl = restaurant.getPhotos();
    }
}
