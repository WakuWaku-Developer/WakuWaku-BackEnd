package dev.backend.wakuwaku.domain.likes.controller;

import dev.backend.wakuwaku.domain.likes.dto.request.LikesRequest;
import dev.backend.wakuwaku.domain.likes.dto.response.LikesResponse;
import dev.backend.wakuwaku.domain.likes.entity.Likes;
import dev.backend.wakuwaku.domain.likes.service.LikesService;
import dev.backend.wakuwaku.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wakuwaku/v1/likes")
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likesService;

    @GetMapping("/{memberId}")
    public BaseResponse<AllLikesResponse> getAllLikes(@PathVariable("memberId") Long memberId, @PageableDefault(size = 10) Pageable pageable) {
        AllLikesResponse paginatedLikes = likesService.getPaginatedLikesList(memberId, pageable);

        return new BaseResponse<>(paginatedLikes);
    }

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
