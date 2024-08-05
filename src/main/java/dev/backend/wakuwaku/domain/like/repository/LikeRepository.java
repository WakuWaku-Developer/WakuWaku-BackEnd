package dev.backend.wakuwaku.domain.like.repository;

import dev.backend.wakuwaku.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like,Long> {
    @Query("SELECT l FROM Likes l WHERE l.memberId.id = :memberId AND l.restaurantId.id = :restaurantId")
    Like findByMemberIdAndRestaurantId(@Param("memberId") Long memberId, @Param("restaurantId") Long restaurantId);

}
