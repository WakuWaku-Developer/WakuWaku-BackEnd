package dev.backend.wakuwaku.global.infra.oauth.google.authcode;

import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import dev.backend.wakuwaku.domain.oauth.oauthcode.AuthCodeRequestUrlProvider;
import dev.backend.wakuwaku.global.infra.oauth.google.GoogleOauthConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class GoogleAuthCodeRequestUrlProvider implements AuthCodeRequestUrlProvider {

    private final GoogleOauthConfig googleOauthConfig;

    @Override
    public OauthServerType supportServer() {
        return OauthServerType.GOOGLE;
    }

    @Override
    public String provide() {
        return UriComponentsBuilder
                .fromUriString("https://accounts.google.com/o/oauth2/auth")
                .queryParam("response_type", "code")
                .queryParam("client_id", googleOauthConfig.clientId())
                .queryParam("redirect_uri", googleOauthConfig.redirectUri())
                .queryParam("scope", String.join(" ", googleOauthConfig.scope()))
                .queryParam("state", googleOauthConfig.state())
                .queryParam("token_uri", googleOauthConfig.tokenUri())
                .queryParam("resource_uri", googleOauthConfig.resourceUri())
                .build()
                .toUriString();
    }
}
