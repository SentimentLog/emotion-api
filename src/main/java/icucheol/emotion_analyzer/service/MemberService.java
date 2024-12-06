package icucheol.emotion_analyzer.service;

import icucheol.emotion_analyzer.domain.Member;
import icucheol.emotion_analyzer.dto.MemberRequestDto;
import icucheol.emotion_analyzer.dto.MemberResponseDto;
import icucheol.emotion_analyzer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // PasswordEncoder 주입

    /**
     * 회원 등록
     */
    public MemberResponseDto registerMember(MemberRequestDto memberRequestDto) {
        // 이메일 중복 확인
        if (memberRepository.findByEmail(memberRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(memberRequestDto.getPassword());

        // 회원 생성 및 저장
        Member member = Member.builder()
                .username(memberRequestDto.getUsername())
                .email(memberRequestDto.getEmail())
                .password(encryptedPassword) // 암호화된 비밀번호 저장
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

    /**
     * 모든 회원 조회
     */
    public List<MemberResponseDto> findAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(member -> new MemberResponseDto(
                        member.getId(),
                        member.getUsername(),
                        member.getEmail()
                ))
                .collect(Collectors.toList());
    }

    /**
     * 이메일로 회원 삭제
     */
    public void deleteMemberByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        memberRepository.delete(member);
    }

    /**
     * 회원 정보 업데이트
     */
    public MemberResponseDto updateMember(Long memberId, MemberRequestDto memberRequestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 이메일 중복 확인 (본인의 이메일은 허용)
        if (!member.getEmail().equals(memberRequestDto.getEmail()) &&
                memberRepository.findByEmail(memberRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호가 제공된 경우 암호화하여 저장
        if (memberRequestDto.getPassword() != null && !memberRequestDto.getPassword().isBlank()) {
            member.setPassword(passwordEncoder.encode(memberRequestDto.getPassword()));
        }

        member.setUsername(memberRequestDto.getUsername());
        member.setEmail(memberRequestDto.getEmail());

        Member updatedMember = memberRepository.save(member);

        return new MemberResponseDto(
                updatedMember.getId(),
                updatedMember.getUsername(),
                updatedMember.getEmail()
        );
    }



    /**
     * 비밀번호 변경
     */
    public void changePassword(String email, String newPassword) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }

}