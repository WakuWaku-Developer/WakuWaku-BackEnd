package dev.backend.wakuwaku.domain.member.entity;


import dev.backend.wakuwaku.domain.StatusEntity;
import dev.backend.wakuwaku.domain.oauth.dto.OauthId;
import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import dev.backend.wakuwaku.domain.oauth.dto.Role;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor//(access = PROTECTED)
@Table(
        name = "member_table",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "oauth_id_unique",
                        columnNames = {
                                "oauth_server_id",
                                "oauth_server"
                        }
                )
        }
)

public class Member extends StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(nullable = false, name = "oauth_server_id", insertable = false, updatable = false)
    private String oauthServerId;

    @Enumerated(STRING)
    @Column(nullable = false, name = "oauth_server", insertable = false, updatable = false)
    private OauthServerType oauthServerType;

    @Column
    private String birthday;

    @Column
    private String email;


    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(length = 200)
    private String profileImageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;


    @Embedded
    private OauthId oauthId;
    public String oauthServerId() {
        return oauthServerId;
    }

    public OauthServerType oauthServer() {
        return oauthServerType;
    }

    @Builder
    public Member(String oauthServerId, OauthServerType oauthServerType, String email,
                  String birthday, String nickname, String profileImageUrl, Role role) {
        this.oauthServerId = oauthServerId;
        this.oauthServerType = oauthServerType;
        this.email = email;
        this.birthday = birthday;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.role = Role.GUEST;
    }
}
