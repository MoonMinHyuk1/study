package tech.study.global.resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import tech.study.domain.member.entity.Member;
import tech.study.domain.member.repository.MemberRepository;
import tech.study.global.annotation.Auth;
import tech.study.global.exception.ApplicationException;
import tech.study.global.util.SecurityUtil;

import static tech.study.global.exception.ErrorCode.DONT_EXIST_MEMBER;

@Component
@RequiredArgsConstructor
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(Auth.class);
        boolean isMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        return hasAnnotation && isMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Long id = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(DONT_EXIST_MEMBER));

        return member;
    }
}
