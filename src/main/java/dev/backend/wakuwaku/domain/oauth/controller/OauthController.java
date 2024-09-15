package dev.backend.wakuwaku.domain.oauth.controller;

import dev.backend.wakuwaku.domain.likes.service.LikesService;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.oauth.dto.request.LoginRequest;
import dev.backend.wakuwaku.domain.oauth.dto.response.OAuthLoginResponse;
import dev.backend.wakuwaku.domain.oauth.service.OauthService;
import dev.backend.wakuwaku.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OauthController {
    private final OauthService oauthService;

    private final LikesService likesService;

    @PostMapping("/login")
    BaseResponse<OAuthLoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Member member = oauthService.login(loginRequest.getOauthServerType(), loginRequest.getCode());
        List<String> placeIdOfLikesRestaurants = likesService.getLikedRestaurantPlaceIds(member);

        return new BaseResponse<>(OAuthLoginResponse.builder()
                                                         .id(member.getId())
                                                         .profileUrl(member.getProfileImageUrl())
                                                         .nickname(member.getNickname())
                                                         .accessToken(member.getOauthServerId())
                                                         .likedRestaurantPlaceIds(placeIdOfLikesRestaurants)
                                                         .build());
    }
}
