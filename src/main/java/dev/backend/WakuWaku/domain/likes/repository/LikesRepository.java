package dev.backend.wakuwaku.domain.likes.repository;

import dev.backend.wakuwaku.domain.likes.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes,Long> {
    @Query("SELECT l FROM Likes l WHERE l.memberId.id = :memberId AND l.restaurantId.id = :restaurantId")
    Likes findByMemberIdAndRestaurantId(@Param("memberId") Long memberId, @Param("restaurantId") Long restaurantId);

}
