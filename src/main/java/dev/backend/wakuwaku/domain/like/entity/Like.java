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
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(length = 1)
    private String likeStatus;


    @Builder
    public Like(Member member, Restaurant restaurant, String likeStatus) {
        this.member = member;
        this.restaurant = restaurant;
        this.likeStatus = likeStatus;
    }

    public void updateLikeStatus(String likeStatus){
        this.likeStatus = likeStatus;
    }
}
