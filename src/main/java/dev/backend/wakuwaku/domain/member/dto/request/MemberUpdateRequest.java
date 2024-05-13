package dev.backend.wakuwaku.domain.member.dto.request;

import dev.backend.wakuwaku.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateRequest {

    private String nickname;
    private String profileImageUrl;

    public MemberUpdateRequest(String nickname, String profileImageUrl)
    {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public void setMemberPassword(String nickname) {
        this.nickname = nickname;
    }

    public void setMemberName(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Member toMemberEntity() {
        return Member.builder()
                .nickname(this.nickname)
                .profileImageUrl(this.profileImageUrl)
                .build();
    }
}
