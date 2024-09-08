package dev.backend.wakuwaku.domain.likes.controller;

import dev.backend.wakuwaku.domain.likes.dto.request.LikesRequest;
import dev.backend.wakuwaku.domain.likes.dto.response.LikesResponse;
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
    public BaseResponse<LikesResponse> pushLikes(@RequestBody LikesRequest likesRequest) {
        Likes likes = likesService.addLikes(likesRequest.getMemberId(), likesRequest.getRestaurantId());

        LikesResponse response = new LikesResponse(likes);

        return new BaseResponse<>(response);
    }



    @DeleteMapping("/delete")
    public BaseResponse<Void> deleteLike(@RequestBody LikesRequest likesRequest) {
        likesService.deleteLikes(likesRequest.getMemberId(), likesRequest.getRestaurantId());
        return new BaseResponse<>();
    }
}
