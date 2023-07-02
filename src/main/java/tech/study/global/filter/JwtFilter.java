package tech.study.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.study.global.exception.ApplicationException;
import tech.study.global.jwt.TokenProvider;
import tech.study.global.response.ApplicationResponse;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt = tokenProvider.resolveToken(request);

        //유효성 검사 후 토큰에 해당하는 Authentication 을 가져와 SecurityContext 에 저장
        try {
            tokenProvider.validateToken(jwt);
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (ApplicationException e) {
            ObjectMapper objectMapper = new ObjectMapper();
            response.setStatus(e.getErrorCode().getStatus().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            objectMapper.writeValue(response.getWriter(), new ApplicationResponse(e.getErrorCode()));
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        AntPathMatcher pathMatcher = new AntPathMatcher();
        String[] excludePath = {"/api", "/api/member/signup", "/api/member/signin",
                                "/", "/signup",
                                "/static/**", "/css/**", "/error"};
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(ep -> pathMatcher.match(ep, path));
    }
}
