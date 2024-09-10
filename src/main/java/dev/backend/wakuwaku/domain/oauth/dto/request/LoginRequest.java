package dev.backend.wakuwaku.domain.oauth.dto.request;

import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    private OauthServerType oauthServerType;

    private String code;

    public OauthServerType getOauthServerType() {
        return oauthServerType;
    }

    public void createOauthServerType(OauthServerType oauthServerType) {
        this.oauthServerType = oauthServerType;
    }

    public void createCode(String code) {
        this.code = code;
    }
}
