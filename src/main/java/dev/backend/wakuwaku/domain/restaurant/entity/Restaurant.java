package dev.backend.wakuwaku.domain.restaurant.entity;

import dev.backend.wakuwaku.domain.BaseEntity;
import dev.backend.wakuwaku.global.infra.google.places.old.Result;
import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto.PlacePhoto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    private List<String> photos = new ArrayList<>();

    private Number userRatingsTotal;

    private Number rating;

    @Builder
    public Restaurant(Result result) {
        this.placeId = result.getPlace_id();
        this.name = result.getName();
        this.lat = result.getGeometry().getLocation().getLat();
        this.lng = result.getGeometry().getLocation().getLng();
        this.photos = result.getPhotos().stream()
                .map(PlacePhoto::getPhoto_reference)
                .toList();
        this.userRatingsTotal = result.getUser_ratings_total();
        this.rating = result.getRating();
    }
}
