package dev.backend.wakuwaku.global.infra.oauth.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * application.yml에 oauth.kakao로 설정된 정보들을 통해 생성
 */
@ConfigurationProperties(prefix = "oauth.kakao")
public record KakaoOauthConfig(
        String redirectUri,
        String clientId,
        String clientSecret,
        String[] scope) {
}