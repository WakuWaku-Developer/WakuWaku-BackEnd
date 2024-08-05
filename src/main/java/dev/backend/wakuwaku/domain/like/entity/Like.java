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
@Table(name = "likes_table")
public class Like extends StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member memberId;

    @ManyToOne
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Restaurant restaurantId;

    @Column
    private String likeStatus;

    @Builder
    public Like(Long id, Member memberId, Restaurant restaurantId, String likeStatus) {
        this.id = id;
        this.memberId = memberId;
        this.restaurantId = restaurantId;
        this.likeStatus = likeStatus;
    }

    public void updateLikeStatus(String likeStatus){
        this.likeStatus = likeStatus;
    }
}
