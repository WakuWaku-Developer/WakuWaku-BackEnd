package dev.backend.wakuwaku.domain.likes.service;

import dev.backend.wakuwaku.domain.likes.dto.LikesStatusType;
import dev.backend.wakuwaku.domain.likes.dto.request.LikesRestaurantRequest;
import dev.backend.wakuwaku.domain.likes.dto.response.AllLikesResponse;
import dev.backend.wakuwaku.domain.likes.entity.Likes;
import dev.backend.wakuwaku.domain.likes.repository.LikesRepository;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.member.repository.MemberRepository;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import dev.backend.wakuwaku.domain.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static dev.backend.wakuwaku.global.exception.WakuWakuException.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LikesService {

    private final LikesRepository likesRepository;
    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;

    /**
     * 찜 추가
     * @param memberId
     * @param restaurantInfo
     * @return
     */
    public Likes addLikes(Long memberId, LikesRestaurantRequest restaurantInfo) {
        Restaurant restaurant = getRestaurant(restaurantInfo);

        return likesRepository.findByMemberIdAndRestaurantId(memberId, restaurant.getId())
                .map(likes -> {
                    // 이미 찜이 되어있을 때
                    if (LikesStatusType.Y.equals(likes.getLikesStatus())) {
                        throw ALREADY_LIKED_EXCEPTION;
                    }

                    // 찜 상태 N -> Y로 업데이트
                    likes.updateLikeStatus(LikesStatusType.Y);  // "N" 상태인 경우 "Y"로 업데이트

                    return likes;
                })
                .orElseGet(() -> createNewLike(memberId, restaurant));  // 찜이 없으면 새로 생성
    }

    private Restaurant getRestaurant(LikesRestaurantRequest restaurantInfo) {
        Restaurant restaurant = Restaurant.builder()
                                          .placeId(restaurantInfo.getPlaceId())
                                          .name(restaurantInfo.getName())
                                          .lat(restaurantInfo.getLat())
                                          .lng(restaurantInfo.getLng())
                                          .photo(restaurantInfo.getPhoto())
                                          .userRatingsTotal(restaurantInfo.getUserRatingsTotal())
                                          .rating(restaurantInfo.getRating())
                                          .build();

        return restaurantRepository.findByPlaceId(restaurantInfo.getPlaceId())
                                   .orElseGet(
                                           () -> restaurantRepository.save(restaurant)
                                   );
    }

    /**
     * 찜 삭제
     * @param likesId
     * @return
     */
    public void deleteLikes(Long likesId) {
        Likes likes = likesRepository.findById(likesId)
                                     .orElseThrow(() -> LIKE_NOT_FOUND_EXCEPTION);  // 찜이 없으면 예외 발생

        likes.updateLikeStatus(LikesStatusType.N);  // 찜 상태를 "N"으로 변경
    }

    /**
     * 새로운 찜 (식당/멤버) 데이터 생성
     * @param memberId
     * @param restaurant
     * @return
     */
    private Likes createNewLike(Long memberId, Restaurant restaurant) {
        // 멤버/식당 id 검증
        Member member = memberRepository.findById(memberId).orElseThrow(() -> NOT_FOUND_MEMBER_INFO);

        // newLike 생성
        Likes newLikes = Likes.builder()
                .member(member)
                .restaurant(restaurant)
                .likesStatus(LikesStatusType.Y)
                .build();

        likesRepository.save(newLikes);

        return newLikes;
    }

    public List<String> getLikedRestaurantPlaceIds(Member member) {
        List<Likes> likesList = likesRepository.findAllByMemberId(member.getId());

        if (likesList == null || likesList.isEmpty()) {
            return new ArrayList<>();
        }

        return likesList.stream()
                        .map(likes -> likes.getRestaurant().getPlaceId())
                        .toList();
    }

    public AllLikesResponse getPaginatedLikesList(Long memberId, Pageable pageable) {
        Page<Likes> likesPage = likesRepository.findAllByMemberId(memberId, pageable);

        List<Likes> likes = likesPage.getContent();

        List<Restaurant> likesRestaurants = restaurantRepository.findAllByPlaceIds(likes.stream()
                                                                                                .map(likesList -> likesList.getRestaurant().getPlaceId())
                                                                                                .toList());

        int totalPages = likesPage.getTotalPages();

        return new AllLikesResponse(likesRestaurants, totalPages);
    }
}

