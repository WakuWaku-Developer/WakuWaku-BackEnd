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

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final RestaurantRepository restaurantRepository;

    public Long pushLike(Member member, Restaurant restaurant) {
        // 1. 식당 정보가 DB에 있는지 확인
        Optional<Restaurant> existingRestaurant = restaurantRepository.findByPlaceId(restaurant.getPlaceId());

        Restaurant targetRestaurant;
        if (existingRestaurant.isPresent()) {
            targetRestaurant = existingRestaurant.get();
        } else {
            // 식당 정보가 없으면 새롭게 저장
            targetRestaurant = restaurantRepository.save(restaurant);
        }

        // 2. 찜 정보 저장
        Like like = Like.builder()
                .member(member)
                .restaurant(targetRestaurant)
                .likeStatus("Y")
                .name(restaurant.getName())
                .lat(restaurant.getLat())
                .lng(restaurant.getLng())
                .retaurantPhotoUrl(restaurant.getPhoto().isEmpty() ? null : restaurant.getPhoto())
                .userRatingsTotal(restaurant.getUserRatingsTotal())
                .rating(restaurant.getRating())
                .build();

        likeRepository.save(like);

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
}
