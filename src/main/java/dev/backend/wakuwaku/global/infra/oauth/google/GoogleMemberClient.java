package dev.backend.wakuwaku.global.infra.oauth.google;

import dev.backend.wakuwaku.domain.oauth.dto.OauthMember;
import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import dev.backend.wakuwaku.global.infra.oauth.client.OauthMemberClient;
import dev.backend.wakuwaku.global.infra.oauth.google.client.GoogleApiClient;
import dev.backend.wakuwaku.global.infra.oauth.google.dto.GoogleMemberResponse;
import dev.backend.wakuwaku.global.infra.oauth.google.dto.GoogleToken;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
public class GoogleMemberClient implements OauthMemberClient {

    private final GoogleApiClient googleApiClient;
    private final GoogleOauthConfig googleOauthConfig;

    public GoogleMemberClient(GoogleApiClient googleApiClient, GoogleOauthConfig googleOauthConfig) {
        this.googleApiClient = googleApiClient;
        this.googleOauthConfig = googleOauthConfig;
    }

    @Override
    public OauthServerType supportServer() {
        return OauthServerType.GOOGLE;
    }

    @Override
    public OauthMember fetch(String authCode) {
        // 구글 OAuth 서버에 인증 코드를 전달하여 액세스 토큰을 요청합니다.
        GoogleToken tokenInfo = googleApiClient.fetchToken(tokenRequestParams(authCode));

        // 액세스 토큰을 사용하여 사용자 정보를 요청합니다.
        GoogleMemberResponse googleMemberResponse = googleApiClient.fetchMember(tokenInfo.access_token());

        // 가져온 사용자 정보를 도메인 객체로 변환하여 반환합니다.
        return googleMemberResponse.toDomain();
    }

    private MultiValueMap<String, String> tokenRequestParams(String authCode) {
        // 액세스 토큰을 요청하기 위한 매개변수를 생성합니다.
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", googleOauthConfig.clientId());
        params.add("client_secret", googleOauthConfig.clientSecret());
        params.add("code", authCode);
        params.add("redirect_uri", googleOauthConfig.redirectUri());
        params.add("token_uri", googleOauthConfig.tokenUri());
        params.add("resource_uri", googleOauthConfig.resourceUri());
        return params;
    }
}
