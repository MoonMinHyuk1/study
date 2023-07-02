package tech.study.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.study.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority = Authority.USER;

    @Enumerated(EnumType.STRING)
    private Provider provider = Provider.NONE;

    @Builder
    public Member(String username, String email, String password, Authority authority, Provider provider) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.provider = provider;
    }
}
