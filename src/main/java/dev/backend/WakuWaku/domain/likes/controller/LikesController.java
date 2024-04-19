package dev.backend.wakuwaku.domain.likes.controller;

import dev.backend.wakuwaku.domain.likes.dto.request.LikesPushRequest;
import dev.backend.wakuwaku.domain.likes.dto.response.GetLikesResponse;
import dev.backend.wakuwaku.domain.likes.entity.Likes;
import dev.backend.wakuwaku.domain.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("wakuwaku/v1/likes")
@RequiredArgsConstructor
@Slf4j
public class LikesController {
    private final LikesService likesService;

    // 좋아요 추가 또는 제거
    @PostMapping("/push")
    public ResponseEntity<String> pushLikes(@RequestBody LikesPushRequest likesPushRequest) {
        // memberId와 restaurantId의 유효성 검사 추가
        Long memberId = likesPushRequest.getMemberId();
        Long restaurantId = likesPushRequest.getRestaurantId();

        if (memberId == null || restaurantId == null) {
            return ResponseEntity.badRequest().body("Invalid memberId or restaurantId");
        }

        Long id = likesService.likesPush(memberId, restaurantId);

        // 좋아요 상태에 따른 메시지 반환
        String message = id != null ? "찜하기 성공" : "찜하기 실패";

        return ResponseEntity.ok().body(message);
    }

    @GetMapping("/list")
    public ResponseEntity<List<GetLikesResponse>> findAll(){
        List<Likes> likesList = likesService.findAll();

        return ResponseEntity.ok().body(likesList.stream()
                .map(GetLikesResponse::new)
                .collect(Collectors.toList()));
    }

}
