package dev.backend.wakuwaku.domain.likes.entity;


import dev.backend.wakuwaku.domain.common.entity.StatusEntity;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "likes_table")
public class Likes extends StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member memberId;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurantId;

    @Column
    private String likesStatus;

    @Builder
    public Likes(Long id, Member memberId, Restaurant restaurantId, String likesStatus) {
        this.id = id;
        this.memberId = memberId;
        this.restaurantId = restaurantId;
        this.likesStatus = likesStatus;
    }

}
