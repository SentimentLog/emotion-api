package icucheol.emotion_analyzer.service;

import icucheol.emotion_analyzer.domain.Member;
import icucheol.emotion_analyzer.dto.MemberRequestDto;
import icucheol.emotion_analyzer.dto.MemberResponseDto;
import icucheol.emotion_analyzer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 등록
     */
    public MemberResponseDto registerMember(MemberRequestDto memberRequestDto) {
        // 이메일 중복 확인
        if (memberRepository.findByEmail(memberRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 회원 생성 및 저장
        Member member = Member.builder()
                .username(memberRequestDto.getUsername())
                .email(memberRequestDto.getEmail())
                .password(memberRequestDto.getPassword()) // 실제 환경에서는 비밀번호 암호화 필요
                .build();

        Member savedMember = memberRepository.save(member);

        // 저장된 회원 데이터를 응답 DTO로 변환하여 반환
        return new MemberResponseDto(
                savedMember.getId(),
                savedMember.getUsername(),
                savedMember.getEmail()
        );
    }

    /**
     * 이메일로 회원 검색
     */
    public Optional<MemberResponseDto> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(member -> new MemberResponseDto(
                        member.getId(),
                        member.getUsername(),
                        member.getEmail()
                ));
    }
}