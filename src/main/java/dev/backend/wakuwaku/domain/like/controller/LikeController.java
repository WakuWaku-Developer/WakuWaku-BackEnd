package dev.backend.wakuwaku.domain.like.controller;

import dev.backend.wakuwaku.domain.like.dto.request.LikePushRequest;
import dev.backend.wakuwaku.domain.like.dto.response.GetLikeResponse;
import dev.backend.wakuwaku.domain.like.entity.Like;
import dev.backend.wakuwaku.domain.like.service.LikeService;
import dev.backend.wakuwaku.global.exception.WakuWakuException;
import dev.backend.wakuwaku.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("wakuwaku/v1/likes")
@RequiredArgsConstructor
@Slf4j
public class LikeController {
    private final LikeService likesService;

    // 좋아요 추가 또는 제거
    @PostMapping("/push")
    public BaseResponse<String> pushLikes(@RequestBody LikePushRequest likesPushRequest) {
        // memberId와 restaurantId의 유효성 검사 추가
        Long memberId = likesPushRequest.getMemberId();
        Long restaurantId = likesPushRequest.getRestaurantId();

        if (memberId == null) {
            throw WakuWakuException.NOT_EXISTED_MEMBER_INFO; // 예외
        } else if (restaurantId == null) {
            throw WakuWakuException.NOT_EXISTED_PLACE_ID; // 예외
        }

        Long id = likesService.likesPush(memberId, restaurantId);

        // 좋아요 상태에 따른 메시지 반환
        String message = id != null ? "찜하기 성공" : "찜하기 실패";

        return new BaseResponse<>(message);
    }

    @GetMapping("/list")
    public BaseResponse<List<GetLikeResponse>> findAll(){
        List<Like> likesList = likesService.findAll();

        return new BaseResponse<>(likesList.stream()
                .map(GetLikeResponse::new)
                .collect(Collectors.toList()));
    }

}