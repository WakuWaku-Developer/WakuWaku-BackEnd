package dev.backend.wakuwaku.global.infra.oauth.google.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.backend.wakuwaku.domain.oauth.dto.OauthId;
import dev.backend.wakuwaku.domain.oauth.dto.OauthMember;

import static dev.backend.wakuwaku.domain.oauth.dto.OauthServerType.GOOGLE;
@JsonNaming(value = SnakeCaseStrategy.class)
public record GoogleMemberResponse(
        String resultcode,
        String message,
        Response response
) {

    public OauthMember toDomain() {
        return OauthMember.builder()
                .oauthId(new OauthId(String.valueOf(response.id), GOOGLE))
                .email(response.email)
                .nickname(response.given_name +" "+response.family_name)
                .profileImageUrl(response.picture)
                .build();
    }
    @JsonNaming(value = SnakeCaseStrategy.class)
    public record Response(
            String id,
            String email,
            String given_name,
            String family_name,
            String picture
    ) {
    }

}
