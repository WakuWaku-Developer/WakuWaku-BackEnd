package dev.backend.wakuwaku.domain.restaurant.service;

import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import dev.backend.wakuwaku.domain.restaurant.repository.RestaurantRepository;
import dev.backend.wakuwaku.global.infra.google.places.old.Result;
import dev.backend.wakuwaku.global.infra.google.places.old.details.GooglePlacesDetailsService;
import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.GooglePlacesTextSearchService;
import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto.Geometry;
import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto.LatLngLiteral;
import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto.PlacePhoto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {
    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private GooglePlacesDetailsService googlePlacesDetailsService;

    @Mock
    private GooglePlacesTextSearchService googlePlacesTextSearchService;

    @InjectMocks
    private RestaurantService restaurantService;

    private static final String PLACE_ID = "ChIJAQCl79GMGGARZheneHqgIUs";

    private static final String NAME = "우동신";

    private final List<PlacePhoto> photos = new ArrayList<>();

    private Result result;

    @BeforeEach
    void setUp() {
        LatLngLiteral latLngLiteral = new LatLngLiteral(35.6864899, 139.6969979);
        PlacePhoto placePhoto = new PlacePhoto("https://lh3.googleusercontent.com/places/ANXAkqFVKvKKJF9PvO5CRH_QqzNwhk3fVw7fet05L49Zt2OFwMvPzX1wwC2qdXs3x2zO4x08fFsJojhgvga3GYWbb16PRO471kMleWY=s1600-w800");

        Geometry geometry = new Geometry(latLngLiteral);

        photos.add(placePhoto);

        result = Result.builder()
                .place_id(PLACE_ID)
                .name(NAME)
                .rating(4.1)
                .user_ratings_total(3929)
                .geometry(geometry)
                .photos(photos).build();
    }

    @Test
    @DisplayName("저장할 식당이 없다면 식당 저장 성공")
    void saveSuccess() {
        // given
        Restaurant restaurant = new Restaurant(result);

        given(restaurantRepository.findByPlaceId(anyString())).willReturn(Optional.empty());
        given(restaurantRepository.save(any(Restaurant.class))).willReturn(restaurant);

        // when
        Restaurant savedRestaurant = restaurantService.save(restaurant);

        // then
        assertThat(savedRestaurant).isEqualTo(restaurant);
        then(restaurantRepository).should().save(restaurant);
    }

    @Test
    @DisplayName("요청한 Place Id가 있다면 식당 저장 X")
    void saveFailed() {
        // given
        Restaurant restaurant = new Restaurant(result);
        Restaurant duplicateRestaurant = new Restaurant(result);

        given(restaurantRepository.findByPlaceId(PLACE_ID)).willReturn(Optional.of(restaurant));

        // when
        Restaurant existingRestaurant = restaurantService.save(duplicateRestaurant);

        // then
        assertThat(existingRestaurant.getPlaceId()).isEqualTo(PLACE_ID);
        then(restaurantRepository).should(never()).save(duplicateRestaurant);
    }

    @Test
    void findById() {
        // given
        Restaurant restaurant = new Restaurant(result);

        given(restaurantRepository.findById(anyLong())).willReturn(Optional.of(restaurant));

        // when
        Restaurant restaurantById = restaurantService.findById(anyLong());

        // then
        assertThat(restaurantById).isEqualTo(restaurant);
        then(restaurantRepository).should().findById(anyLong());
    }

    @Test
    void getSimpleRestaurants() {
        // given
        List<Result> results = new ArrayList<>();
        results.add(result);

        Restaurant restaurant = new Restaurant(result);

        given(googlePlacesTextSearchService.textSearch(anyString())).willReturn(results);

        // when
        List<Restaurant> simpleRestaurants = restaurantService.getSimpleRestaurants(anyString());

        // then
        assertThat(simpleRestaurants).hasSize(1);
        assertThat(simpleRestaurants.get(0).getPlaceId()).isEqualTo(restaurant.getPlaceId());
        assertThat(simpleRestaurants.get(0).getName()).isEqualTo(restaurant.getName());
        assertThat(simpleRestaurants.get(0).getLat()).isEqualTo(restaurant.getLat());
        assertThat(simpleRestaurants.get(0).getLng()).isEqualTo(restaurant.getLng());
        assertThat(simpleRestaurants.get(0).getRating()).isEqualTo(restaurant.getRating());
        assertThat(simpleRestaurants.get(0).getUserRatingsTotal()).isEqualTo(restaurant.getUserRatingsTotal());
        assertThat(simpleRestaurants.get(0).getPhotos()).isEqualTo(restaurant.getPhotos());

        then(googlePlacesTextSearchService).should().textSearch(anyString());
    }

    @Test
    void getDetailsRestaurant() {
        // given
        given(googlePlacesDetailsService.detailsSearch(PLACE_ID)).willReturn(result);

        // when
        Result details = restaurantService.getDetailsRestaurant(PLACE_ID);

        // then
        assertThat(details).isEqualTo(result);
    }
}