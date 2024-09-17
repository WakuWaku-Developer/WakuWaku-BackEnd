package dev.backend.wakuwaku.global.infra.oauth.kakao;

import dev.backend.wakuwaku.domain.oauth.dto.OauthMember;
import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import dev.backend.wakuwaku.global.infra.oauth.client.OauthMemberClient;
import dev.backend.wakuwaku.global.infra.oauth.kakao.client.KakaoApiClient;
import dev.backend.wakuwaku.global.infra.oauth.kakao.dto.KakaoMemberResponse;
import dev.backend.wakuwaku.global.infra.oauth.kakao.dto.KakaoToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;


/**
 * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
 * 위 링크 API 문서와 Description
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoMemberClient implements OauthMemberClient {
    private final KakaoApiClient kakaoApiClient;

    private final KakaoOauthConfig kakaoOauthConfig;

    @Override
    public OauthServerType supportServer() {
        return OauthServerType.KAKAO;
    }

    /**
     (1) - 먼저 Auth Code를 통해서 AccessToken을 조회
     (2) - AccessToken을 가지고 회원 정보를 받아옴
     (3) - 회원 정보를 OauthMember 객체로 변환
     */
    @Override
    public OauthMember fetch(String authCode) {
        log.info("KakaoMemberClient Class fetch Method authCode = {}", authCode);

        KakaoToken tokenInfo = kakaoApiClient.fetchToken(tokenRequestParams(authCode)); // (1)

        log.info("tokenInfo.accessToken() = {}", tokenInfo.accessToken());
        log.info("tokenInfo = {}", tokenInfo);

        KakaoMemberResponse kakaoMemberResponse = kakaoApiClient.fetchMember("Bearer " + tokenInfo.accessToken(), APPLICATION_FORM_URLENCODED_VALUE);  // (2)

        log.info("kakaoMemberResponse.toDomain().getEmail() = {}", kakaoMemberResponse.toDomain().getEmail());
        log.info("kakaoMemberResponse = {]", kakaoMemberResponse);

        return kakaoMemberResponse.toDomain();  // (3)
    }

    private MultiValueMap<String, String> tokenRequestParams(String authCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoOauthConfig.getClientId());
        params.add("code", authCode);
        params.add("client_secret", kakaoOauthConfig.getClientSecret());

        log.info("params.get(\"grant_type\") = {}", params.get("grant_type"));
        log.info("params.get(\"client_id\") = {}", params.get("client_id"));
        log.info("params.get(\"code\") = {}", params.get("code"));
        log.info("params.get(\"client_secret\") = {}", params.get("client_secret"));

        log.info("params = {}", params);

        return params;
    }
}