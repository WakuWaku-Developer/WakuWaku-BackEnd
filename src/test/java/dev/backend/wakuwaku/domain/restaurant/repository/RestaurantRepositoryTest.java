package dev.backend.wakuwaku.domain.restaurant.repository;

import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import dev.backend.wakuwaku.global.infra.google.places.dto.Location;
import dev.backend.wakuwaku.global.infra.google.places.dto.Photo;
import dev.backend.wakuwaku.global.infra.google.places.dto.Places;
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

    private static final double LAT = 99.99;

    private static final double LNG = 11.11;

    private static final String PHOTO_URL = "test-photo-url";

    private static final int USER_RATINGS_TOTAL = 999;

    private static final double RATING = 4.35;

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
        assertThat(restaurant).isNotNull();
        assertThat(restaurant.getPlaceId()).isEqualTo(saveRestaurant.getPlaceId());
        assertThat(restaurant.getName()).isEqualTo(saveRestaurant.getName());
        assertThat(restaurant.getLat()).isEqualTo(saveRestaurant.getLat());
        assertThat(restaurant.getLng()).isEqualTo(saveRestaurant.getLng());
        assertThat(restaurant.getRating()).isEqualTo(saveRestaurant.getRating());
        assertThat(restaurant.getUserRatingsTotal()).isEqualTo(saveRestaurant.getUserRatingsTotal());
        assertThat(restaurant.getPhoto()).isEqualTo(saveRestaurant.getPhoto());
        assertThat(restaurant).isEqualTo(saveRestaurant);
    }

    @DisplayName("PlaceId 리스트에 포함된 모든 Restaurant 조회")
    @Test
    void findAllByPlaceIds() {
        // given
        Restaurant restaurant1 = restaurantRepository.save(createRestaurant(1));
        Restaurant restaurant2 = restaurantRepository.save(createRestaurant(2));
        Restaurant restaurant3 = restaurantRepository.save(createRestaurant(3));
        Restaurant restaurant4 = restaurantRepository.save(createRestaurant(4));

        List<String> placeIds = new ArrayList<>();

        placeIds.add(restaurant1.getPlaceId());
        placeIds.add(restaurant2.getPlaceId());
        placeIds.add(restaurant3.getPlaceId());

        // when
        List<Restaurant> restaurants = restaurantRepository.findAllByPlaceIds(placeIds);

        // then
        assertThat(restaurants).isNotEmpty()
                                     .hasSize(3)
                                     .contains(restaurant1)
                                     .contains(restaurant2)
                                     .contains(restaurant3)
                                     .isNotIn(restaurant4);
    }

    private Restaurant createRestaurant(int number) {
        return Restaurant.builder()
                         .placeId(PLACE_ID + number)
                         .name(NAME)
                         .lat(LAT)
                         .lng(LNG)
                         .photo(PHOTO_URL)
                         .userRatingsTotal(USER_RATINGS_TOTAL)
                         .rating(RATING)
                         .build();
    }
}
