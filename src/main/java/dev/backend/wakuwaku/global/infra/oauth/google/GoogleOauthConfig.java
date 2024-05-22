package dev.backend.wakuwaku.global.infra.oauth.google;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.google")
public record GoogleOauthConfig (

        String redirectUri,
        String clientId,
        String clientSecret,
        String[] scope,
        String tokenUri,
        String resourceUri,
        String state
){
}
