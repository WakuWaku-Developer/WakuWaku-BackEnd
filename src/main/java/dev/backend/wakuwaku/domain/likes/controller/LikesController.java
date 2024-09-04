package dev.backend.wakuwaku.domain.likes.controller;

import dev.backend.wakuwaku.domain.likes.dto.request.LikeDeleteRequest;
import dev.backend.wakuwaku.domain.likes.dto.request.LikePushRequest;
import dev.backend.wakuwaku.domain.likes.entity.Likes;
import dev.backend.wakuwaku.domain.likes.service.LikesService;
import dev.backend.wakuwaku.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wakuwaku/v1/likes")
@RequiredArgsConstructor
@Slf4j
public class LikesController {
    private final LikesService likesService;

    @PostMapping("/push")
    public BaseResponse<String> pushLikes(@RequestBody LikePushRequest likePushRequest) {
        Likes likes = likesService.addLikes(likePushRequest.getMemberId(), likePushRequest.getRestaurantId());
        String message = likes.getId() != null ? "찜하기 성공" : "찜하기 실패";
        return new BaseResponse<>(message);
    }


    @DeleteMapping("/delete")
    public BaseResponse<String> deleteLike(@RequestBody LikeDeleteRequest likeDeleteRequest) {
        boolean result = likesService.deleteLikes(likeDeleteRequest.getMemberId(), likeDeleteRequest.getRestaurantId());
        String message = result ? "찜 삭제 성공" : "찜 삭제 실패";
        return new BaseResponse<>(message);
    }
}
