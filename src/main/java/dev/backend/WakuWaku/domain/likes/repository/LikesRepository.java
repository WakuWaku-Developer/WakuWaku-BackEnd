package dev.backend.wakuwaku.domain.likes.repository;

import dev.backend.wakuwaku.domain.likes.entity.LikesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<LikesEntity,Long> {
    @Query("SELECT l FROM LikesEntity l WHERE l.memberId.id = :memberId AND l.restaurantId.id = :restaurantId")
    LikesEntity findByMemberIdAndRestaurantId(@Param("memberId") Long memberId, @Param("restaurantId") Long restaurantId);

}
