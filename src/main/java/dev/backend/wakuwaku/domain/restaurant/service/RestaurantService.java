package dev.backend.wakuwaku.domain.restaurant.service;

import dev.backend.wakuwaku.domain.restaurant.dto.response.Restaurants;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import dev.backend.wakuwaku.domain.restaurant.repository.RestaurantRepository;
import dev.backend.wakuwaku.global.infra.google.places.details.GooglePlacesDetailsService;
import dev.backend.wakuwaku.global.infra.google.places.dto.Places;
import dev.backend.wakuwaku.global.infra.google.places.textsearch.GooglePlacesTextSearchService;
import dev.backend.wakuwaku.global.infra.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dev.backend.wakuwaku.domain.restaurant.service.constant.RestaurantServiceConstant.CALL_GOOGLE_API_PAGE;
import static dev.backend.wakuwaku.domain.restaurant.service.constant.SearchWordConstant.*;
import static dev.backend.wakuwaku.global.exception.WakuWakuException.INVALID_PARAMETER;
import static dev.backend.wakuwaku.global.exception.WakuWakuException.INVALID_SEARCH_WORD;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final GooglePlacesTextSearchService googlePlacesTextSearchService;
    private final GooglePlacesDetailsService googlePlacesDetailsService;
    private final RedisService redisService;

    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.findByPlaceId(restaurant.getPlaceId())
                .orElseGet(
                        () -> restaurantRepository.save(restaurant)
                );
    }

    @Transactional(readOnly = true)
    public Restaurant findById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(
                        () -> INVALID_PARAMETER
                );
    }

    public Restaurants getSimpleRestaurants(String searchWord, int page) {
        if (searchWord == null || searchWord.isEmpty() || searchWord.matches("^[ \t]*$")) {
            throw INVALID_SEARCH_WORD;
        }

        List<Places> placesByRedis = redisService.getPlacesByRedis(searchWord, page);

        if ((placesByRedis == null || placesByRedis.isEmpty()) && page == CALL_GOOGLE_API_PAGE) {
            List<Places> places = googlePlacesTextSearchService.getRestaurantsByTextSearch(JAPAN_WITH_SPACE + searchWord.trim() + FRONT_OF_RESTAURANT + RESTAURANT, 0);

            redisService.savePlaces(searchWord, places);

            List<Places> firstPlaces = redisService.getPlacesByRedis(searchWord, page);

            List<Restaurant> restaurants = firstPlaces.stream()
                    .map(Restaurant::new)
                    .toList();

            int totalPage = redisService.getTotalPage(searchWord);

            return new Restaurants(restaurants, totalPage);
        }

        if (placesByRedis == null || placesByRedis.isEmpty()) {
            return new Restaurants();
        }

        List<Restaurant> restaurants = placesByRedis.stream()
                .map(Restaurant::new)
                .toList();

        int placesSize = redisService.getTotalPage(searchWord);

        return new Restaurants(restaurants, placesSize);
    }

    public Places getDetailsRestaurant(String placeId) {
        return googlePlacesDetailsService.getRestaurantByDetailsSearch(placeId);
    }
}
