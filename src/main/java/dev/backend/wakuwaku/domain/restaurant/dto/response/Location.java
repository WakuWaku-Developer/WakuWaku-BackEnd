package dev.backend.wakuwaku.domain.restaurant.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Location {
    private final double lat;
    private final double lng;

    @Builder
    public Location(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
