package dev.backend.wakuwaku.domain.favorite.entity;

import dev.backend.wakuwaku.domain.BaseEntity;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
@Table(name = "favorites_table")
public class Favorite extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoritesId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    //@JoinColumn
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}