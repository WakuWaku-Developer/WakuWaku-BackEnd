package dev.backend.wakuwaku.domain.likes.entity;

import dev.backend.wakuwaku.domain.StatusEntity;
import dev.backend.wakuwaku.domain.likes.dto.LikesStatusType;
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
public class Likes extends StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column
    private LikesStatusType likesStatus;


    @Builder
    public Likes(Member member, Restaurant restaurant, LikesStatusType likesStatus) {
        this.member = member;
        this.restaurant = restaurant;
        this.likesStatus = likesStatus;
    }

    public void updateLikeStatus(LikesStatusType likesStatus){
        this.likesStatus = likesStatus;
    }


    public void createId(Long id){
        this.id = id;
    }
}
