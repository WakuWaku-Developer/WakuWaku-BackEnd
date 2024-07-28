package dev.backend.wakuwaku.domain.oauth.dto.request;

import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
    private OauthServerType oauthServerType;
    private String code;

    // Getters and setters

    public OauthServerType getOauthServerType() {
        return oauthServerType;
    }

    public void setOauthServerType(OauthServerType oauthServerType) {
        this.oauthServerType = oauthServerType;
    }
}
