package dev.backend.wakuwaku.domain.restaurant.controller;

import dev.backend.wakuwaku.domain.restaurant.dto.response.DetailsInfoRestaurantResponse;
import dev.backend.wakuwaku.domain.restaurant.dto.response.SimpleInfoRestaurantResponse;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import dev.backend.wakuwaku.domain.restaurant.service.RestaurantService;
import dev.backend.wakuwaku.global.infra.google.places.Places;
import dev.backend.wakuwaku.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wakuwaku/v1/restaurant")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping
    public BaseResponse<List<SimpleInfoRestaurantResponse>> getSimpleInfoRestaurants(@RequestParam("search") String searchWord) {

        List<Restaurant> restaurants = restaurantService.getSimpleRestaurants(searchWord);

        return new BaseResponse<>(restaurants.stream()
                .map(SimpleInfoRestaurantResponse::new)
                .toList());
    }

    @GetMapping("/{placeId}/details")
    public BaseResponse<DetailsInfoRestaurantResponse> getDetailsInfoRestaurant(@PathVariable("placeId") String placeId) {
        Places places = restaurantService.getDetailsRestaurant(placeId);

        return new BaseResponse<>(new DetailsInfoRestaurantResponse(places));
    }
}
