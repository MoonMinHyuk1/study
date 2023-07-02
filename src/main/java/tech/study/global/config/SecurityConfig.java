package tech.study.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import tech.study.global.config.jwt.JwtAccessDeniedHandler;
import tech.study.global.config.jwt.JwtAuthenticationEntryPoint;
import tech.study.global.config.jwt.JwtSecurityConfig;
import tech.study.global.service.AuthService;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthService authService;

    private final JwtSecurityConfig jwtSecurityConfig;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.csrf().disable()        //csrf 보안 설정 사용 X
                .logout().disable()         //로그아웃 사용 X
                .formLogin().disable()      //폼 로그인 사용 X

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler))

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(request -> request               // 사용자가 보내는 요청에 인증 절차 수행 필요
                        .requestMatchers("/api", "/api/member/signup", "/api/member/signin",
                                "/", "/signup",
                                "/static/**", "/css/**", "/error").permitAll()    // 해당 URL은 인증 절차 수행 생략 가능
                        .anyRequest().authenticated())                  // 나머지 요청들은 모두 인증 절차 수행해야함

                .oauth2Login(login -> login  // OAuth2를 통한 로그인 사용
                        .defaultSuccessUrl("/api", true)
                        .userInfoEndpoint(point -> point    // 사용자가 로그인에 성공하였을 경우, 해당 서비스 로직을 타도록 설정
                                .userService(authService)))

                .apply(jwtSecurityConfig)

                .and()
                .build();
    }
}
