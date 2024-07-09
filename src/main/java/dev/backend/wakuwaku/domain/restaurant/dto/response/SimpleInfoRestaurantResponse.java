package dev.backend.wakuwaku.domain.restaurant.dto.response;

import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import dev.backend.wakuwaku.global.infra.google.places.dto.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SimpleInfoRestaurantResponse {
    private String placeId;
    private String name;
    private Number rating;
    private Number userRatingsTotal;
    private Location location;
    private List<String> photoUrl;

    public SimpleInfoRestaurantResponse(Restaurant restaurant) {
        this.placeId = restaurant.getPlaceId();
        this.name = restaurant.getName();
        this.rating = restaurant.getRating();
        this.userRatingsTotal = restaurant.getUserRatingsTotal();
        this.location = new Location(restaurant.getLat(), restaurant.getLng());
        this.photoUrl = restaurant.getPhotos();
    }
}
