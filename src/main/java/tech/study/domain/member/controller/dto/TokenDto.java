package tech.study.domain.member.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {

    private String accessToken;

    private String refreshToken;
}
