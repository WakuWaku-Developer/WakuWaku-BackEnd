package dev.backend.wakuwaku.domain.likes.service;

import dev.backend.wakuwaku.domain.likes.entity.Likes;
import dev.backend.wakuwaku.domain.likes.repository.LikesRepository;
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
public class LikesService {
    private final LikesRepository likesRepository;
    private final MemberService memberService;
    private final RestaurantService restaurantService;

    //TODO: 수정필요

    public Long likesPush(Long memberId, Long restaurantId) {
        Likes likes = likesRepository.findByMemberIdAndRestaurantId(memberId, restaurantId);

        if (likes != null) {
            // 좋아요 상태 변경
            String status = likes.getLikesStatus();
            if ("Y".equals(status)) {
                likes.setLikesStatus("N");
            } else if ("N".equals(status)) {
                likes.setLikesStatus("Y");
            }
            likesRepository.save(likes);
        } else {
            // 좋아요 엔터티가 존재하지 않으면 새로운 레코드 생성
            Member member = memberService.findById(memberId);
            Restaurant restaurant = restaurantService.findById(restaurantId);

            if (member != null && restaurant != null) {
                likes = Likes.builder()
                        .memberId(member)
                        .restaurantId(restaurant)
                        .likesStatus("Y")
                        .build();
                likes = likesRepository.save(likes); // save() 후에 엔티티를 다시 할당
            } else {
                log.error("Member or Restaurant not found");
            }
        }
        return likes != null ? likes.getId() : null; // 엔티티가 null이 아닌 경우에만 ID 반환
    }

    /**
    찾기
     */
    public List<Likes> findAll(){
        return likesRepository.findAll();
    }

}


