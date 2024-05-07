package dev.backend.wakuwaku.domain.member.entity;


import dev.backend.wakuwaku.domain.StatusEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table (name = "member_table")
public class Member extends StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Email
    private String memberEmail;
    @Column
    @NotNull
    private String memberPassword;

    @Column
    private String memberName;

    @Column
    private String memberNickname;
    @Column
    private String memberBirth;
    @Builder
    public Member(String memberEmail, String memberPassword, String memberName, String memberNickname, String memberBirth) {
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.memberNickname = memberNickname;
        this.memberBirth = memberBirth;

    }
}
