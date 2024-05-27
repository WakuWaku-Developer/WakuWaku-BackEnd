package dev.backend.wakuwaku.global.infra.oauth.google.client;

import dev.backend.wakuwaku.global.infra.oauth.google.GoogleOauthConfig;
import dev.backend.wakuwaku.global.infra.oauth.google.dto.GoogleMemberResponse;
import dev.backend.wakuwaku.global.infra.oauth.google.dto.GoogleToken;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleApiClientImpl implements GoogleApiClient {

    private final RestTemplate restTemplate;
    private final GoogleOauthConfig googleOauthConfig;

    public GoogleApiClientImpl(RestTemplate restTemplate, GoogleOauthConfig googleOauthConfig) {
        this.restTemplate = restTemplate;
        this.googleOauthConfig = googleOauthConfig;
    }

    @Override
    public GoogleToken fetchToken(MultiValueMap<String, String> params) {
        // 1. 구글 OAuth 서버로부터 액세스 토큰을 요청합니다.
        // POST 요청을 통해 액세스 토큰을 요청합니다.
        return restTemplate.postForObject(googleOauthConfig.tokenUri(), params, GoogleToken.class);
    }

    @Override
    public GoogleMemberResponse fetchMember(String accessToken) {
        // 2. 액세스 토큰을 사용하여 사용자 정보를 가져옵니다.
        // 헤더에 액세스 토큰을 포함하여 요청합니다.
        String url = googleOauthConfig.resourceUri() + "?access_token=" + accessToken;
        return restTemplate.getForObject(url, GoogleMemberResponse.class);
    }
}
