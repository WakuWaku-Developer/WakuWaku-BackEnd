package dev.backend.wakuwaku.domain.restaurant.repository;

import dev.backend.wakuwaku.domain.restaurant.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
}
