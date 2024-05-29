package dev.backend.wakuwaku.domain.restaurant.repository;

import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import dev.backend.wakuwaku.global.infra.google.places.old.Result;
import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto.Geometry;
import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto.LatLngLiteral;
import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto.PlacePhoto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RestaurantRepositoryTest {
    @Autowired
    private RestaurantRepository restaurantRepository;

    private Result result;

    private final List<PlacePhoto> photos = new ArrayList<>();

    private Restaurant saveRestaurant;

    @BeforeEach
    void setUp() {
        LatLngLiteral latLngLiteral = new LatLngLiteral(35.6864899, 139.6969979);
        PlacePhoto placePhoto = new PlacePhoto("https://lh3.googleusercontent.com/places/ANXAkqFVKvKKJF9PvO5CRH_QqzNwhk3fVw7fet05L49Zt2OFwMvPzX1wwC2qdXs3x2zO4x08fFsJojhgvga3GYWbb16PRO471kMleWY=s1600-w800");

        Geometry geometry = new Geometry(latLngLiteral);
        photos.add(placePhoto);

        result = Result.builder()
                .place_id("ChIJAQCl79GMGGARZheneHqgIUs")
                .name("우동신")
                .rating(4.1)
                .user_ratings_total(3929)
                .geometry(geometry)
                .photos(photos).build();

        saveRestaurant = restaurantRepository.save(new Restaurant(result));
    }

    @DisplayName("동일한 Place Id를 가진 Restaurant 조회")
    @Test
    void findByPlaceId() {
        // given
        String placeId = result.getPlace_id();

        // when
        Restaurant restaurant = restaurantRepository.findByPlaceId(placeId).orElse(null);

        // then
        assertThat(restaurant.getPlaceId()).isEqualTo(saveRestaurant.getPlaceId());
        assertThat(restaurant.getName()).isEqualTo(saveRestaurant.getName());
        assertThat(restaurant.getLat()).isEqualTo(saveRestaurant.getLat());
        assertThat(restaurant.getLng()).isEqualTo(saveRestaurant.getLng());
        assertThat(restaurant.getRating()).isEqualTo(saveRestaurant.getRating());
        assertThat(restaurant.getUserRatingsTotal()).isEqualTo(saveRestaurant.getUserRatingsTotal());
        assertThat(restaurant.getPhotos()).hasSize(1);
        assertThat(restaurant).isEqualTo(saveRestaurant);
    }

    @DisplayName("동일한 Place Id를 가진 Restaurant 삭제")
    @Test
    void deleteByPlaceId() {
        // given
        String placeId = result.getPlace_id();

        // when
        restaurantRepository.deleteByPlaceId(placeId);

        // then
        Restaurant restaurant = restaurantRepository.findByPlaceId(placeId).orElse(null);

        assertThat(restaurant).isNull();
    }
}