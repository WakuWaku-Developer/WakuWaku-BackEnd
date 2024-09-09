package dev.backend.wakuwaku.domain.likes.controller;

import dev.backend.wakuwaku.domain.likes.dto.request.PushLikesRequest;
import dev.backend.wakuwaku.domain.likes.dto.response.AllLikesResponse;
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
    public BaseResponse<LikesResponse> pushLikes(@RequestBody PushLikesRequest pushLikesRequest) {
        Likes likes = likesService.addLikes(pushLikesRequest.getMemberId(), pushLikesRequest.getRestaurantInfo());

        return new BaseResponse<>(new LikesResponse(likes));
    }



    @DeleteMapping("/delete/{likesId}")
    public BaseResponse<Void> deleteLike(@PathVariable("likesId") Long likesId) {
        likesService.deleteLikes(likesId);

        return new BaseResponse<>();
    }
}
