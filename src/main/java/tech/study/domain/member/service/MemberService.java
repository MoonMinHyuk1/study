package tech.study.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.study.domain.member.controller.dto.TokenDto;
import tech.study.domain.member.dto.SigninRequest;
import tech.study.domain.member.dto.SignupRequest;
import tech.study.domain.member.entity.Authority;
import tech.study.domain.member.entity.Member;
import tech.study.domain.member.entity.Provider;
import tech.study.domain.member.repository.MemberRepository;
import tech.study.domain.member.validate.MemberValidate;
import tech.study.global.jwt.TokenProvider;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberValidate memberValidate;

    private final MemberRepository memberRepository;

    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public void signup(SignupRequest request) {
        memberValidate.duplicateEmail(request.getEmail());
        memberValidate.checkPassword(request.getPassword(), request.getPasswordCheck());

        Member member = Member.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .authority(Authority.USER)
                .provider(Provider.NONE)
                .build();
        memberRepository.save(member);
    }

    public TokenDto signin(SigninRequest request) {
        Authentication authentication = createAuthentication(request.getEmail(), request.getPassword());

        //인증 정보를 기반으로 JWT 토큰 생성
        return tokenProvider.createTokenDto(authentication);
    }

    private Authentication createAuthentication(String principal, String credentials) {
        //Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(principal, credentials);

        //실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return authentication;
    }
}
