package dev.backend.wakuwaku.domain.restaurant.entity;

import dev.backend.wakuwaku.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "restaurant_table")

public class RestaurantEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String restaurantId;

    @Column
    private String restaurantName;

    @Column
    private String restaurantAddress;

    @Column
    private String restaurantRating;



    @Builder
    public RestaurantEntity(String restaurantId, String restaurantName, String restaurantAddress, String restaurantRating) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantRating = restaurantRating;

    }

}
