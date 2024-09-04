package dev.backend.wakuwaku.domain.likes.service;

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

import java.util.Optional;

import static dev.backend.wakuwaku.global.exception.WakuWakuException.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LikesService {

    private final LikesRepository likesRepository;
    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;

    public Likes addLikes(Long memberId, Long restaurantId) {
        // 찜이 이미 존재하는지 확인
        // Orelseget
        Optional<Likes> existingLike = likesRepository.findByMemberIdAndRestaurantId(memberId, restaurantId);

        if (existingLike.isPresent()) {
            // 이미 찜되어 있는 경우
            Likes likes = existingLike.get();
            if ("Y".equals(likes.getLikesStatus())) {
                throw ALREADY_LIKED_EXCEPTION;
            } else {
                // 찜 상태가 "N"인 경우 다시 "Y"로 업데이트
                likes.updateLikeStatus("Y");
                return likesRepository.save(likes);
            }
        } else {
            // 찜이 존재하지 않는 경우, 새로 추가
            Member member = memberRepository.findById(memberId).orElseThrow(() -> NOT_FOUND_MEMBER_INFO);
            Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> NOT_FOUND_RESTAURANT_INFO);

            Likes newLikes = Likes.builder()
                    .member(member)
                    .restaurant(restaurant)
                    .likesStatus("Y")
                    .build();

            likesRepository.save(newLikes);

            return newLikes;
        }
    }

    /**
     * 찜 삭제
     * @param memberId
     * @param restaurantId
     * @return 찜 삭제 성공 여부
     */
    public boolean deleteLikes(Long memberId, Long restaurantId) {
        Optional<Likes> like = likesRepository.findByMemberIdAndRestaurantId(memberId, restaurantId);
        if (like.isPresent()) {
            // 찜 상태를 "N"으로 변경
            Likes existingLikes = like.get();
            existingLikes.updateLikeStatus("N");
            likesRepository.save(existingLikes);
            return true;
        } else {
            throw LIKE_NOT_FOUND_EXCEPTION;
        }
    }
}

