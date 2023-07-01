package tech.study.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.study.domain.user.dto.SignupRequest;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Builder
    public Member(SignupRequest request, Authority authority, Provider provider) {
        this.username = request.getUsername();
        this.email = request.getEmail();
        this.password = request.getPassword();
        this.authority = authority;
        this.provider = provider;
    }
}
