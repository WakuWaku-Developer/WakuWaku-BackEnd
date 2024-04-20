package dev.backend.wakuwaku.domain.restaurant.dto;


import lombok.*;

@Data
public class RestaurantDto {
    private Long id;

    private String restaurantId;

    private String restaurantName;

    private String restaurantAddress;

    private String restaurantRating;

}
