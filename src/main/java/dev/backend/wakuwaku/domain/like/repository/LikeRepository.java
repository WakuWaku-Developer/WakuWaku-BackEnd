package dev.backend.wakuwaku.domain.like.repository;

import dev.backend.wakuwaku.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT l FROM Like l WHERE l.member.id = :memberId AND l.restaurant.placeId = :restaurantPlaceId")
    Optional<Like> findByMemberIdAndRestaurantId(@Param("memberId") Long memberId, @Param("restaurantId") String restaurantPlaceId);

    // 찜 리스트
    @Query("SELECT l FROM Like l WHERE l.member.id = :memberId AND l.likeStatus = 'Y'")
    List<Like> findLikeStatusAllByMemberId(@Param("memberId") Long memberId);
}
