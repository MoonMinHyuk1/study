package tech.study.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.study.domain.user.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Boolean existsByEmail(String email);
}
