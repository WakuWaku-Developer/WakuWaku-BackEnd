package dev.backend.wakuwaku.domain.like.service;

import dev.backend.wakuwaku.domain.like.entity.Like;
import dev.backend.wakuwaku.domain.like.repository.LikeRepository;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.member.service.MemberService;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import dev.backend.wakuwaku.domain.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LikeService {
    private final LikeRepository likesRepository;
    private final MemberService memberService;
    private final RestaurantService restaurantService;

    //TODO: 수정필요
    public Long likesPush(Long memberId, Long restaurantId) {
        Like like = likesRepository.findByMemberIdAndRestaurantId(memberId, restaurantId);

        if (like != null) {
            // 좋아요 상태 변경
            String status = like.getLikeStatus();
            if ("Y".equals(status)) {
                like.updateLikeStatus("N");
            } else if ("N".equals(status)) {
                like.updateLikeStatus("Y");
            }
            likesRepository.save(like);
        } else {
            // 좋아요 엔터티가 존재하지 않으면 새로운 레코드 생성
            Member member = memberService.findById(memberId);
            Restaurant restaurant = restaurantService.findById(restaurantId);

            if (member != null && restaurant != null) {
                like = Like.builder()
                        .memberId(member)
                        .restaurantId(restaurant)
                        .likeStatus("Y")
                        .build();
                like = likesRepository.save(like); // save() 후에 엔티티를 다시 할당
            } else {
                log.error("Member or Restaurant not found");
            }
        }
        return like != null ? like.getId() : null; // 엔티티가 null이 아닌 경우에만 ID 반환
    }

    /**
     찾기
     */
    public List<Like> findAll(){
        return likesRepository.findAll();
    }

}