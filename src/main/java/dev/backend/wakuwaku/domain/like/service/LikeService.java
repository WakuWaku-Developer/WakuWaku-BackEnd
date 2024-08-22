package dev.backend.wakuwaku.domain.like.service;

import dev.backend.wakuwaku.domain.like.entity.Like;
import dev.backend.wakuwaku.domain.like.repository.LikeRepository;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import dev.backend.wakuwaku.domain.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import static dev.backend.wakuwaku.global.exception.WakuWakuException.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final RestaurantRepository restaurantRepository;

    public Long pushLike(Member member, Restaurant restaurant) {
        if (member == null) {
            throw NOT_EXISTED_MEMBER_INFO;
        }
        if (restaurant == null) {
            throw NOT_EXISTED_PLACE_ID;
        }

        // 1. 찜 테이블에서 해당 식당 정보가 있는지 확인
        Optional<Like> existingLike = likeRepository.findByMemberIdAndRestaurantId(member.getId(), restaurant.getId());

        Like like;

        if (existingLike.isPresent()) {
            // 이미 존재하는 찜 정보가 있으면 해당 정보를 반환
            like = existingLike.get();
        } else {
            // 찜 테이블에 식당 정보가 없으면 Restaurant 테이블에서 확인 후 저장
            Optional<Restaurant> existingRestaurant = restaurantRepository.findById(restaurant.getId());

            Restaurant targetRestaurant;

            if (existingRestaurant.isPresent()) {
                targetRestaurant = existingRestaurant.get();
            } else {
                targetRestaurant = restaurantRepository.save(restaurant);
            }

            // 새로운 찜 정보 생성
            like = Like.builder()
                    .member(member)
                    .restaurant(targetRestaurant)
                    .likeStatus("Y")
                    .name(targetRestaurant.getName())
                    .lat(targetRestaurant.getLat())
                    .lng(targetRestaurant.getLng())
                    .retaurantPhotoUrl(targetRestaurant.getPhoto().isEmpty() ? null : targetRestaurant.getPhoto())
                    .userRatingsTotal(targetRestaurant.getUserRatingsTotal())
                    .rating(targetRestaurant.getRating())
                    .build();

            likeRepository.save(like);
        }

        return like.getId();
    }

    /**
     *
     * @param memberId
     * @return 찜 (찜상태: Y) 리스트
     */
    public List<Like> findLikeStatusAllByMemberId(Long memberId) {
        return likeRepository.findLikeStatusAllByMemberId(memberId);
    }

    /**
     * 찜 삭제
     * @param memberId
     * @param restaurantId
     * @return 찜 삭제 성공 여부
     */
    public boolean deleteLike(Long memberId, Long restaurantId) {
        Optional<Like> like = likeRepository.findByMemberIdAndRestaurantId(memberId, restaurantId);
        if (like.isPresent()) {
            likeRepository.delete(like.get());
            return true;
        }
        return false;
    }
}
