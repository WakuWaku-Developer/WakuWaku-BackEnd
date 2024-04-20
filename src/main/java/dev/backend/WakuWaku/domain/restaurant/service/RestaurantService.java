package dev.backend.wakuwaku.domain.restaurant.service;


import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import dev.backend.wakuwaku.domain.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(
                        IllegalStateException::new
                );
    }




}
