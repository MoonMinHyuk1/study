package tech.study.domain.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.study.domain.member.controller.dto.TokenDto;
import tech.study.domain.member.dto.MemberResponse;
import tech.study.domain.member.dto.SigninRequest;
import tech.study.domain.member.dto.SignupRequest;
import tech.study.domain.member.entity.Member;
import tech.study.domain.member.service.MemberService;
import tech.study.global.annotation.Auth;
import tech.study.global.response.ApplicationResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    private static final String COOKIE_NAME = "RefreshToken";

    private static final long COOKIE_EXPIRE = 60 * 60 * 24 * 7;

    @GetMapping
    public ApplicationResponse<MemberResponse> getMember(@Auth Member member) {
        return new ApplicationResponse(memberService.getMember(member));
    }

    @PostMapping("/signup")
    public ApplicationResponse signup(@Validated @RequestBody SignupRequest request) {
        memberService.signup(request);
        return new ApplicationResponse();
    }

    @PostMapping("/signin")
    public ApplicationResponse<String> signin(@Validated @RequestBody SigninRequest request,
                                              HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        TokenDto tokenDto = memberService.signin(request);
        setRefreshToken(httpServletRequest, httpServletResponse, tokenDto.getRefreshToken());

        return new ApplicationResponse(tokenDto.getAccessToken());
    }

    @PostMapping("/reissue")
    public ApplicationResponse<String> reissue(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                               @CookieValue(name = "RefreshToken", required = false, defaultValue = "") String refreshToken) {
        TokenDto tokenDto = memberService.reissue(httpServletRequest, refreshToken);
        setRefreshToken(httpServletRequest, httpServletResponse, tokenDto.getRefreshToken());

        return new ApplicationResponse(tokenDto.getAccessToken());
    }

    private void setRefreshToken(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, refreshToken)
                .domain(request.getServerName())
                .maxAge(COOKIE_EXPIRE)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .build();
        response.setHeader("Set-Cookie", cookie.toString());
    }
}
