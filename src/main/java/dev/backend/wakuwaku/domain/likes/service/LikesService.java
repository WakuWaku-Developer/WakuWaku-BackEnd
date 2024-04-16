package dev.backend.wakuwaku.domain.likes.service;


import dev.backend.wakuwaku.domain.likes.entity.LikesEntity;
import dev.backend.wakuwaku.domain.likes.repository.LikesRepository;
import dev.backend.wakuwaku.domain.member.entity.MemberEntity;
import dev.backend.wakuwaku.domain.member.service.MemberService;
import dev.backend.wakuwaku.domain.restaurant.entity.RestaurantEntity;
import dev.backend.wakuwaku.domain.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class LikesService {
    @Autowired
    private final LikesRepository likesRepository;
    @Autowired
    private final MemberService memberService;

    private final RestaurantService restaurantService;

    //TODO: 수정필요

    public Long likesPush(Long memberId, Long restaurantId) {
        LikesEntity likesEntity = likesRepository.findByMemberIdAndRestaurantId(memberId, restaurantId);

        if (likesEntity != null) {
            // 좋아요 상태 변경
            String status = likesEntity.getLikesStatus();
            if ("Y".equals(status)) {
                likesEntity.setLikesStatus("N");
            } else if ("N".equals(status)) {
                likesEntity.setLikesStatus("Y");
            }
            likesRepository.save(likesEntity);
        } else {
            // 좋아요 엔터티가 존재하지 않으면 새로운 레코드 생성
            MemberEntity member = memberService.findById(memberId);
            RestaurantEntity restaurant = restaurantService.findById(restaurantId);

            if (member != null && restaurant != null) {
                likesEntity = LikesEntity.builder()
                        .memberId(member)
                        .restaurantId(restaurant)
                        .likesStatus("Y")
                        .build();
                likesEntity = likesRepository.save(likesEntity); // save() 후에 엔티티를 다시 할당
            } else {
                log.error("Member or Restaurant not found");
            }
        }
        return likesEntity != null ? likesEntity.getId() : null; // 엔티티가 null이 아닌 경우에만 ID 반환
    }



    /*
    찾기
     */
    public List<LikesEntity> findAll(){
        return likesRepository.findAll();
    }

}


