package tech.study.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import tech.study.global.exception.ApplicationException;

import static tech.study.global.exception.ErrorCode.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtil {

    // SecurityContext 에 유저 정보가 저장되는 시점
    // Request 가 들어올 때 JwtFilter 의 doFilter 에서 저장
    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new ApplicationException(DONT_VALIDATE_TOKEN);
        }

        return Long.parseLong(authentication.getName());
    }
}
