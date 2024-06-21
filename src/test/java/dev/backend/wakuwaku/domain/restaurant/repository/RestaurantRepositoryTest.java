package dev.backend.wakuwaku.domain.restaurant.repository;

import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import dev.backend.wakuwaku.global.infra.google.places.dto.Places;
import dev.backend.wakuwaku.global.infra.google.places.dto.Location;
import dev.backend.wakuwaku.global.infra.google.places.dto.Photo;
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

    private Places places;

    private static final String PLACE_ID = "ChIJAQCl79GMGGARZheneHqgIUs";

    private static final String NAME = "우동신";

    private final List<Photo> photos = new ArrayList<>();

    private Restaurant saveRestaurant;

    @BeforeEach
    void setUp() {
        dev.backend.wakuwaku.global.infra.google.places.dto.DisplayName name = new dev.backend.wakuwaku.global.infra.google.places.dto.DisplayName(NAME);

        Location location = new Location(35.686489, 139.697001);

        Photo photo = Photo.builder()
                .photoUrl("https://lh3.googleusercontent.com/places/ANXAkqG2xQHKla3ebHNhRNrgMFi4WB6hGbR6LZTd2ig0PK5qTwkIvk0EP1fzPQ8UXmAt3FcU1Gz0XjYYCQJvFJQQVhAMss2GtKenoAI=s4800-w1440-h810")
                .build();

        photos.add(photo);

        places = Places.builder()
                .id(PLACE_ID)
                .displayName(name)
                .rating(4.1)
                .userRatingCount(3929)
                .location(location)
                .photos(photos)
                .build();

        saveRestaurant = restaurantRepository.save(new Restaurant(places));
    }

    @DisplayName("동일한 Place Id를 가진 Restaurant 조회")
    @Test
    void findByPlaceId() {
        // given
        String placeId = places.getId();

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
}
