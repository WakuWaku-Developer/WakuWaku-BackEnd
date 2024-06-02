package dev.backend.wakuwaku.global.infra.oauth.google.authcode;

import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import dev.backend.wakuwaku.global.infra.oauth.oauthcode.OauthCodeRequestUrlProvider;
import dev.backend.wakuwaku.global.infra.oauth.google.GoogleOauthConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GoogleOauthCodeRequestUrlProvider implements OauthCodeRequestUrlProvider {

    private final GoogleOauthConfig googleOauthConfig;

    @Override
    public OauthServerType supportServer() {
        return OauthServerType.GOOGLE;
    }

    public GoogleOauthCodeRequestUrlProvider(GoogleOauthConfig googleOauthConfig) {
        this.googleOauthConfig = googleOauthConfig;
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
