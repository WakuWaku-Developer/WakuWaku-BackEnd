package dev.backend.wakuwaku.domain.oauth.controller;

import dev.backend.wakuwaku.domain.oauth.dto.request.LoginRequest;
import dev.backend.wakuwaku.domain.oauth.service.OauthService;
import dev.backend.wakuwaku.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;

    @PostMapping("/login")
    BaseResponse<Map<String, Long>> login(@RequestBody LoginRequest loginRequest) {

        Map<String, Long> login = oauthService.login(loginRequest.getOauthServerType(), loginRequest.getCode());
        return new BaseResponse<>(login);
    }
}

