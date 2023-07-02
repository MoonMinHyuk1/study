package tech.study.domain.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.study.domain.member.entity.Member;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {

    private String email;

    private String username;

    @Builder
    public MemberResponse(Member member) {
        this.email = member.getEmail();
        this.username = member.getUsername();
    }
}
