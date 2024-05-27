package dev.backend.wakuwaku.global.infra.oauth.google.client;

import dev.backend.wakuwaku.global.infra.oauth.google.dto.GoogleMemberResponse;
import dev.backend.wakuwaku.global.infra.oauth.google.dto.GoogleToken;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleApiClientImpl implements GoogleApiClient {

    private final RestTemplate restTemplate;

    public GoogleApiClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public GoogleToken fetchToken(MultiValueMap<String, String> params) {
        return restTemplate.postForObject("https://oauth2.googleapis.com/token", params, GoogleToken.class);
    }

    @Override
    public GoogleMemberResponse fetchMember(String accessToken) {
        return restTemplate.getForObject("https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken, GoogleMemberResponse.class);
    }
}
