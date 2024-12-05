package icucheol.emotion_analyzer.repository;

import icucheol.emotion_analyzer.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void testSaveAndFindByEmail() {
        Member member = Member.builder()
                .username("test_man")
                .email("test@test.com")
                .password("password1234")
                .build();
        memberRepository.save(member);

        Optional<Member> foundMember = memberRepository.findByEmail("test@test.com");

        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getUsername()).isEqualTo("test_man");
    }
}
