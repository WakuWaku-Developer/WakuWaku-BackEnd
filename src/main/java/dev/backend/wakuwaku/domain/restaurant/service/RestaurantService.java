package dev.backend.wakuwaku.domain.restaurant.service;

import dev.backend.wakuwaku.domain.restaurant.dto.response.Restaurants;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import dev.backend.wakuwaku.domain.restaurant.repository.RestaurantRepository;
import dev.backend.wakuwaku.global.infra.google.places.details.GooglePlacesDetailsService;
import dev.backend.wakuwaku.global.infra.google.places.dto.Places;
import dev.backend.wakuwaku.global.infra.google.places.textsearch.GooglePlacesTextSearchService;
import dev.backend.wakuwaku.global.infra.redis.util.CacheKeyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private final CacheKeyUtils cacheKeyUtils;

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

    /**
     * 검색어의 빈칸을 모두 제거하여 같은 단어지만 빈 칸으로 인해 중복 캐싱을 방지함.
     * <p>sync 속성을 true 로 하여 캐시에 없는 데이터를 동시에 요청할 시, 첫 번째 요청만 실제 데이터를 조회하여 캐시에 저장한 뒤 나머지 요청은 캐시에 저장된 데이터를 응답
     * <p>Cacheable 어노테이션은 메서드 결과를 캐시에 저장하고, 이후 같은 파라미터로 메서드가 호출될 경우 캐시된 결과를 반환한다. 이는 주로 읽기 작업에 사용된다.
     * 즉, @Cacheable은 메서드 실행 전에 캐시를 확인한다
     */
    @Cacheable(value = "SimpleRestaurantCache", key = "@cacheKeyUtils.removeWhitespace(#searchWord) + '-' + 'page'", cacheManager = "redisCacheManager", sync = true)
    public Restaurants getSimpleRestaurants(String searchWord) {
        if (searchWord == null || searchWord.isEmpty() || searchWord.matches("^[ \t]*$")) {
            throw INVALID_SEARCH_WORD;
        }

        List<Places> places = googlePlacesTextSearchService.getRestaurantsByTextSearch(JAPAN_WITH_SPACE + searchWord.trim() + FRONT_OF_RESTAURANT + RESTAURANT, 0);

        List<Restaurant> restaurants = places.stream()
                .map(Restaurant::new)
                .toList();

        return new Restaurants(restaurants);
    }

    public Places getDetailsRestaurant(String placeId) {
        return googlePlacesDetailsService.getRestaurantByDetailsSearch(placeId);
    }
}
