package dev.backend.wakuwaku.domain.like.service;

import dev.backend.wakuwaku.domain.like.entity.Like;
import dev.backend.wakuwaku.domain.like.repository.LikeRepository;
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
public class LikeService {

    private final LikeRepository likeRepository;
    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;

    public Long addLike(Long memberId, Long restaurantId) {
        // 찜이 이미 존재하는지 확인
        Optional<Like> existingLike = likeRepository.findByMemberIdAndRestaurantId(memberId, restaurantId);

        if (existingLike.isPresent()) {
            // 이미 찜되어 있는 경우
            Like like = existingLike.get();
            if ("Y".equals(like.getLikeStatus())) {
                // 이미 찜 상태가 "Y"이면, "이미 찜되어 있습니다" 메시지를 반환
                log.info("이미 찜되어 있습니다.");
                return null;
            } else {
                // 상태가 "N"인 경우 다시 "Y"로 업데이트
                like.updateLikeStatus("Y");
                likeRepository.save(like);
                return like.getId();
            }
        } else {
            // 찜이 존재하지 않는 경우, 새로 추가
            Member member = memberRepository.findById(memberId).orElseThrow(() -> NOT_EXISTED_MEMBER_INFO);
            Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> NOT_EXISTED_MEMBER_INFO);

            Like newLike = Like.builder()
                    .member(member)
                    .restaurant(restaurant)
                    .likeStatus("Y")
                    .build();

            likeRepository.save(newLike);
            return newLike.getId();
        }
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
            // 찜 상태를 "N"으로 변경
            Like existingLike = like.get();
            existingLike.updateLikeStatus("N");
            likeRepository.save(existingLike);
            return true;
        } else {
            throw LIKE_NOT_FOUND_EXCEPTION;
        }
    }
}

