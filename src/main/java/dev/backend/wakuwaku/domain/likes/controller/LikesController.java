package dev.backend.wakuwaku.domain.likes.controller;

import dev.backend.wakuwaku.domain.likes.dto.request.LikesRequest;
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
    public BaseResponse<String> pushLikes(@RequestBody LikesRequest likesRequest) {
        Likes likes = likesService.addLikes(likesRequest.getMemberId(), likesRequest.getRestaurantId());
        String message = likes.getId() != null ? "찜하기 성공" : "찜하기 실패";
        return new BaseResponse<>(message);
    }


    @DeleteMapping("/delete")
    public BaseResponse<String> deleteLike(@RequestBody LikesRequest likesRequest) {
        boolean result = likesService.deleteLikes(likesRequest.getMemberId(), likesRequest.getRestaurantId());
        String message = result ? "찜 삭제 성공" : "찜 삭제 실패";
        return new BaseResponse<>(message);
    }
}
