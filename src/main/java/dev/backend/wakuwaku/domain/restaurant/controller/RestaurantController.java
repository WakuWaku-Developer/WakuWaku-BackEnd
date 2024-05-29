package dev.backend.wakuwaku.domain.restaurant.controller;

import dev.backend.wakuwaku.domain.restaurant.dto.response.DetailsInfoRestaurantResponse;
import dev.backend.wakuwaku.domain.restaurant.dto.response.SimpleInfoRestaurantResponse;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import dev.backend.wakuwaku.domain.restaurant.service.RestaurantService;
import dev.backend.wakuwaku.global.infra.google.places.old.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wakuwaku/v1/restaurant")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<SimpleInfoRestaurantResponse>> getSimpleInfoRestaurants(@RequestParam("search") String searchWord) {

        List<Restaurant> restaurants = restaurantService.getSimpleRestaurants(searchWord);

        return ResponseEntity.ok().body(restaurants.stream()
                .map(SimpleInfoRestaurantResponse::new)
                .toList());
    }

    @GetMapping("/{placeId}/details")
    public ResponseEntity<DetailsInfoRestaurantResponse> getDetailsInfoRestaurant(@PathVariable("placeId") String placeId) {
        Result detailsRestaurant = restaurantService.getDetailsRestaurant(placeId);

        return ResponseEntity.ok().body(new DetailsInfoRestaurantResponse(detailsRestaurant));
    }
}