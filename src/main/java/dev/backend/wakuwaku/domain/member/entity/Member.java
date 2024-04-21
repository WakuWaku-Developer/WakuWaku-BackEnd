package dev.backend.wakuwaku.domain.member.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table (name = "member_table")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String memberId;

    @Column
    @Email
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    @Builder
    public Member(String memberId, String memberEmail, String memberPassword, String memberName) {
        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberName = memberName;

    }
}
