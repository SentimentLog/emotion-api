package icucheol.emotion_analyzer.repository;

import icucheol.emotion_analyzer.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * 이메일을 기준으로 회원 검색
     * @param email 회원 이메일
     * @return Optional<Member>
     */
    Optional<Member> findByEmail(String email);

    /**
     * 사용자 이름을 기준으로 회원 검색
     * @param username 사용자 이름
     * @return Optional<Member>
     */
    Optional<Member> findByUsername(String username);
}