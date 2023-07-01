package tech.study.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.study.domain.user.dto.SignupRequest;
import tech.study.domain.user.entity.Authority;
import tech.study.domain.user.entity.Member;
import tech.study.domain.user.entity.Provider;
import tech.study.domain.user.repository.MemberRepository;
import tech.study.domain.user.validate.MemberValidate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberValidate memberValidate;

    private final MemberRepository memberRepository;

    @Transactional
    public void signup(SignupRequest request) {
        memberValidate.duplicateEmail(request.getEmail());
        memberValidate.checkPassword(request.getPassword(), request.getPasswordCheck());

        Member member = Member.builder()
                .request(request)
                .authority(Authority.USER)
                .provider(Provider.NONE)
                .build();
        memberRepository.save(member);
    }
}
