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
    private final Location location;
    private final List<String> photoUrl;

    public SimpleInfoRestaurantResponse(Restaurant restaurant) {
        this.placeId = restaurant.getPlaceId();
        this.name = restaurant.getName();
        this.rating = restaurant.getRating();
        this.userRatingsTotal = restaurant.getUserRatingsTotal();
        this.location = Location.builder()
                            .lat(restaurant.getLat())
                            .lng(restaurant.getLng())
                            .build();
        this.photoUrl = restaurant.getPhotos();
    }
}
