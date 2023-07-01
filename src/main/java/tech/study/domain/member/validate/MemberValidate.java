package tech.study.domain.member.validate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.study.domain.member.repository.MemberRepository;
import tech.study.global.exception.ApplicationException;

import static tech.study.global.exception.ErrorCode.DONT_EQUAL_PASSWORD;
import static tech.study.global.exception.ErrorCode.DUPLICATE_EMAIL;

@Component
@RequiredArgsConstructor
public class MemberValidate {

    private final MemberRepository memberRepository;

    public void duplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new ApplicationException(DUPLICATE_EMAIL);
        }
    }

    public void checkPassword(String password, String passwordCheck) {
        if(!password.equals(passwordCheck)) {
            throw new ApplicationException(DONT_EQUAL_PASSWORD);
        }
    }
}
