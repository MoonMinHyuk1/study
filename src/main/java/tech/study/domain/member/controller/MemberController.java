package tech.study.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.study.domain.member.controller.dto.TokenDto;
import tech.study.domain.member.dto.SigninRequest;
import tech.study.domain.member.dto.SignupRequest;
import tech.study.domain.member.service.MemberService;
import tech.study.global.response.ApplicationResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ApplicationResponse signup(@Validated @RequestBody SignupRequest request) {
        memberService.signup(request);
        return new ApplicationResponse();
    }

    @PostMapping("/signin")
    public ApplicationResponse<TokenDto> signin(@Validated @RequestBody SigninRequest request) {
        return new ApplicationResponse(memberService.signin(request));
    }
}
