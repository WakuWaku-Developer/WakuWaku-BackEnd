package dev.backend.wakuwaku.domain.restaurant.entity;

import dev.backend.wakuwaku.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "restaurant_table")
public class Restaurant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String restaurantId;

    private List<String> types = new ArrayList<>();

    private float rating;

    private String displayName; // 다시 봐

    private List<String> location = new ArrayList<>(); // 다시 봐

    private String currentOpeningHours; // 다시 봐

    private List<String> photos = new ArrayList<>(); // 다시 봐

    private String dineIn;

    private String takeout;

    private String delivery;

    private List<String> reviews = new ArrayList<>(); // 다시 봐

    private String shortFormattedAddress;

    @Builder
    public Restaurant(String restaurantId, List<String> types, float rating, String displayName, List<String> location, String currentOpeningHours, List<String> photos, String dineIn, String takeout, String delivery, List<String> reviews) {
        this.restaurantId = restaurantId;
        this.types = types;
        this.rating = rating;
        this.displayName = displayName;
        this.location = location;
        this.currentOpeningHours = currentOpeningHours;
        this.photos = photos;
        this.dineIn = dineIn;
        this.takeout = takeout;
        this.delivery = delivery;
        this.reviews = reviews;
    }
}
