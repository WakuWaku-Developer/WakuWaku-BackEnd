package dev.backend.wakuwaku.domain.member.dto.response;

import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.oauth.dto.OauthId;
import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import dev.backend.wakuwaku.domain.oauth.dto.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetMemberResponse {

    private String email;
    private String oauthServerId;
    private OauthServerType oauthServerType;
    private String birthday;
    private String profileImageUrl;
    private OauthId oauthId;
    private String nickname;
    private Role role;

    public GetMemberResponse(Member memberEntity) {
        this.email = memberEntity.getEmail();
        this.oauthServerId = memberEntity.getOauthServerId();
        this.oauthServerType = memberEntity.getOauthServerType();
        this.birthday = memberEntity.getBirthday();
        this.profileImageUrl = memberEntity.getProfileImageUrl();
        //this.oauthId = memberEntity.getOauthId();
        this.nickname = memberEntity.getNickname();
        // Assume role is fetched from somewhere in Member entity, adjust accordingly
        this.role = Role.USER; // Assuming all members have USER role by default, adjust accordingly
    }
}
