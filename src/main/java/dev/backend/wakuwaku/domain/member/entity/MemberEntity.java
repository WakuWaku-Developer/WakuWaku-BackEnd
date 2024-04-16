package dev.backend.wakuwaku.domain.member.entity;

import dev.backend.wakuwaku.domain.common.entity.StatusEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table (name = "member_table")
public class MemberEntity extends StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String memberId;

    @Column
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    @Builder
    public MemberEntity(String memberId, String memberEmail, String memberPassword, String memberName) {
        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberName = memberName;

    }
}
