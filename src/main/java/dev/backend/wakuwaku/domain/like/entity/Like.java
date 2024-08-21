package dev.backend.wakuwaku.domain.like.entity;

import dev.backend.wakuwaku.domain.StatusEntity;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "likes")
public class Like extends StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ManyToOne 매핑 수정
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "restaurant_place_id")
    private Restaurant restaurant;

    @Column
    private String likeStatus;

    @Column
    private String name;

    @Column
    private double lat;

    @Column
    private double lng;

    @Column
    private String retaurantPhotoUrl;

    @Column
    private int userRatingsTotal;

    @Column
    private double rating;

    @Builder
    public Like(Member member, Restaurant restaurant, String likeStatus,
                String name, double lat, double lng, String retaurantPhotoUrl,
                int userRatingsTotal, double rating) {
        this.member = member;
        this.restaurant = restaurant;
        this.likeStatus = likeStatus;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.retaurantPhotoUrl = retaurantPhotoUrl;
        this.userRatingsTotal = userRatingsTotal;
        this.rating = rating;
    }

    public void updateLikeStatus(String likeStatus) {
        this.likeStatus = likeStatus;
    }
}
