package dev.backend.wakuwaku.domain.likes.service;

import dev.backend.wakuwaku.domain.likes.dto.LikesStatusType;
import dev.backend.wakuwaku.domain.likes.entity.Likes;
import dev.backend.wakuwaku.domain.likes.repository.LikesRepository;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.member.repository.MemberRepository;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import dev.backend.wakuwaku.domain.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * @param restaurantId
     * @return
     */
    public Likes addLikes(Long memberId, Long restaurantId) {
        return likesRepository.findByMemberIdAndRestaurantId(memberId, restaurantId)
                .map(likes -> {
                    // 이미 찜이 되어있을 때
                    if (LikesStatusType.Y.equals(likes.getLikesStatus())) {
                        throw ALREADY_LIKED_EXCEPTION;
                    }
                    // 찜 상태 N -> Y로 업데이트
                    likes.updateLikeStatus(LikesStatusType.Y);  // "N" 상태인 경우 "Y"로 업데이트
                    return likes;
                })
                .orElseGet(() -> createNewLike(memberId, restaurantId));  // 찜이 없으면 새로 생성
    }


    /**
     * 찜 삭제
     * @param memberId
     * @param restaurantId
     * @return
     */
    public void deleteLikes(Long memberId, Long restaurantId) {
        Likes likes = likesRepository.findByMemberIdAndRestaurantId(memberId, restaurantId)
                .orElseThrow(() -> LIKE_NOT_FOUND_EXCEPTION);  // 찜이 없으면 예외 발생

        likes.updateLikeStatus(LikesStatusType.N);  // 찜 상태를 "N"으로 변경
        likesRepository.save(likes);
    }



    /**
     * 새로운 찜 (식당/멤버) 데이터 생성
     * @param memberId
     * @param restaurantId
     * @return
     */
    private Likes createNewLike(Long memberId, Long restaurantId) {
        // 멤버/식당 id 검증
        Member member = memberRepository.findById(memberId).orElseThrow(() -> NOT_FOUND_MEMBER_INFO);
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> NOT_FOUND_RESTAURANT_INFO);

        // newLike 생성
        Likes newLikes = Likes.builder()
                .member(member)
                .restaurant(restaurant)
                .likesStatus(LikesStatusType.Y)
                .build();
        likesRepository.save(newLikes);
        return newLikes;
    }


}

