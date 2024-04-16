package dev.backend.wakuwaku.domain.restaurant.dto;


import dev.backend.wakuwaku.domain.common.entity.BaseEntity;
import dev.backend.wakuwaku.domain.restaurant.entity.RestaurantEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RestaurantDTO extends BaseEntity {
    private Long id;

    private String restaurantId;

    private String restaurantName;

    private String restaurantAddress;

    private String restaurantRating;




    public RestaurantEntity toRestaurantEntity(){

        return RestaurantEntity.builder()
                .restaurantId(restaurantId)
                .restaurantName(restaurantName)
                .restaurantAddress(restaurantAddress)
                .restaurantRating(restaurantRating)
                .build();
    }


}
