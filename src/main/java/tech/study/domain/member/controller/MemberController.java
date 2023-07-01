package tech.study.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.study.domain.user.dto.SignupRequest;
import tech.study.domain.user.service.MemberService;
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
}
