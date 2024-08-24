package dev.backend.wakuwaku.domain.like.controller;

import dev.backend.wakuwaku.domain.like.dto.request.LikeDeleteRequest;
import dev.backend.wakuwaku.domain.like.dto.request.LikePushRequest;
import dev.backend.wakuwaku.domain.like.service.LikeService;
import dev.backend.wakuwaku.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wakuwaku/v1/likes")
@RequiredArgsConstructor
@Slf4j
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/push")
    public BaseResponse<String> pushLike(@RequestBody LikePushRequest likePushRequest) {
        Long id = likeService.addLike(likePushRequest.getMemberId(), likePushRequest.getRestaurantId());
        String message = id != null ? "찜하기 성공" : "찜하기 실패";
        return new BaseResponse<>(message);
    }


    @DeleteMapping("/delete")
    public BaseResponse<String> deleteLike(@RequestBody LikeDeleteRequest likeDeleteRequest) {
        boolean result = likeService.deleteLike(likeDeleteRequest.getMemberId(), likeDeleteRequest.getRestaurantId());
        String message = result ? "찜 삭제 성공" : "찜 삭제 실패";
        return new BaseResponse<>(message);
    }
}
