package dev.backend.wakuwaku.domain.member.dto.request;

import dev.backend.wakuwaku.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateRequest {

    private String nickname;
    private String profileImageUrl;
    private String birthday;

    public MemberUpdateRequest(String nickname, String profileImageUrl, String birthday)
    {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.birthday = birthday;
    }

    public void setMemberPassword(String nickname) {
        this.nickname = nickname;
    }

    public void setMemberName(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
    public void setBirthday(String birthday) {
        this.profileImageUrl = birthday;
    }

    public Member toMemberEntity() {
        return Member.builder()
                .nickname(this.nickname)
                .profileImageUrl(this.profileImageUrl)
                .birthday(this.birthday)
                .build();
    }
}
