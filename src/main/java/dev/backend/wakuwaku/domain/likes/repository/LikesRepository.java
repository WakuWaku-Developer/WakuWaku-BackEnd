package dev.backend.wakuwaku.domain.likes.repository;

import dev.backend.wakuwaku.domain.likes.entity.Likes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Query("SELECT l " +
            "FROM Likes l " +
            "WHERE l.member.id = :memberId " +
            "AND l.restaurant.id = :restaurantId")
    Optional<Likes> findByMemberIdAndRestaurantId(@Param("memberId") Long memberId, @Param("restaurantId") Long restaurantId);

    @Query("select l " +
            "from Likes l " +
            "where l.likesStatus = 'Y' " +
            "and l.member.id = :memberId")
    List<Likes> findAllByMemberId(@Param("memberId") Long memberId);

    @Query("select l " +
            "from Likes l " +
            "where l.likesStatus = 'Y' " +
            "and l.member.id = :memberId")
    Page<Likes> findAllByMemberId(@Param("memberId") Long memberId, Pageable pageable);
}
