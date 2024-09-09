package dev.backend.wakuwaku.domain.restaurant.entity;

import dev.backend.wakuwaku.domain.BaseEntity;
import dev.backend.wakuwaku.global.infra.google.places.dto.Places;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Restaurant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placeId;

    private String name;

    private double lat;

    private double lng;

    private String photo = "";

    private int userRatingsTotal;

    private double rating;

    public Restaurant(Places places) {
        this.placeId = places.getId();
        this.name = places.getDisplayName().getText();
        this.lat = places.getLocation().getLatitude();
        this.lng = places.getLocation().getLongitude();

        if (places.getPhotos() != null && !places.getPhotos().isEmpty()) {
            this.photo = places.getPhotos().get(0).getPhotoUrl();
        }

        this.userRatingsTotal = places.getUserRatingCount();
        this.rating = places.getRating();
    }

    @Builder
    public Restaurant(String placeId, String name, double lat, double lng, String photo, int userRatingsTotal, double rating) {
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
