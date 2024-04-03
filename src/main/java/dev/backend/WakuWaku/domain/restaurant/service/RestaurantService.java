package dev.backend.wakuwaku.domain.restaurant.service;


import dev.backend.wakuwaku.domain.restaurant.entity.RestaurantEntity;
import dev.backend.wakuwaku.domain.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RestaurantService {
    @Autowired
    private final RestaurantRepository restaurantRepository;


    public RestaurantEntity findById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalStateException()
                );
    }




}
