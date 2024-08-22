package dev.backend.wakuwaku.domain.like.controller;

import dev.backend.wakuwaku.domain.like.dto.request.LikeDeleteRequest;
import dev.backend.wakuwaku.domain.like.dto.request.LikePushRequest;
import dev.backend.wakuwaku.domain.like.dto.response.GetLikeResponse;
import dev.backend.wakuwaku.domain.like.entity.Like;
import dev.backend.wakuwaku.domain.like.service.LikeService;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import dev.backend.wakuwaku.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static dev.backend.wakuwaku.global.exception.WakuWakuException.NOT_EXISTED_MEMBER_INFO;
import static dev.backend.wakuwaku.global.exception.WakuWakuException.NOT_EXISTED_PLACE_ID;

@RestController
@RequestMapping("/wakuwaku/v1/likes")
@RequiredArgsConstructor
@Slf4j
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/push")
    public BaseResponse<String> pushLike(@RequestBody LikePushRequest likePushRequest) {
        Member member = likePushRequest.getMember();
        Restaurant restaurant = likePushRequest.getRestaurant();

        if (member == null) {
            throw NOT_EXISTED_MEMBER_INFO;
        } else if (restaurant == null) {
            throw NOT_EXISTED_PLACE_ID;
        }

        Long id = likeService.pushLike(member, restaurant);
        String message = id != null ? "찜하기 성공" : "찜하기 실패";

        return new BaseResponse<>(message);
    }

    @GetMapping("/list")
    public BaseResponse<List<GetLikeResponse>> findAll(@RequestParam Long memberId) {
        List<Like> likes = likeService.findLikeStatusAllByMemberId(memberId);

        List<GetLikeResponse> likeResponses = likes.stream()
                .map(GetLikeResponse::new)
                .collect(Collectors.toList());

        return new BaseResponse<>(likeResponses);
    }

    @DeleteMapping("/delete")
    public BaseResponse<String> deleteLike(@RequestBody LikeDeleteRequest likeDeleteRequest) {
        boolean result = likeService.deleteLike(likeDeleteRequest.getMemberId(), likeDeleteRequest.getRestaurantId());
        String message = result ? "찜 삭제 성공" : "찜 삭제 실패";
        return new BaseResponse<>(message);
    }
}
