package dev.backend.wakuwaku.domain.likes.entity;


import dev.backend.wakuwaku.domain.common.entity.StatusEntity;
import dev.backend.wakuwaku.domain.member.entity.MemberEntity;
import dev.backend.wakuwaku.domain.restaurant.entity.RestaurantEntity;
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
public class LikesEntity extends StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private MemberEntity memberId;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private RestaurantEntity restaurantId;

    @Column
    private String likesStatus;

    @Builder
    public LikesEntity(Long id, MemberEntity memberId, RestaurantEntity restaurantId, String likesStatus) {
        this.id = id;
        this.memberId = memberId;
        this.restaurantId = restaurantId;
        this.likesStatus = likesStatus;
    }





}
