package dev.backend.wakuwaku.domain.restaurant.repository;

import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByPlaceId(String placeId);

    @Query("select r " +
            "from Restaurant r " +
            "where r.placeId in :placeIds")
    List<Restaurant> findAllByPlaceIds(@Param("placeIds") List<String> placeIds);
}
