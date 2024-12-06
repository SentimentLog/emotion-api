package icucheol.emotion_analyzer.service;

import icucheol.emotion_analyzer.domain.Member;
import icucheol.emotion_analyzer.dto.TokenResponseDto;
import icucheol.emotion_analyzer.repository.MemberRepository;
import icucheol.emotion_analyzer.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    // 로그인
    public TokenResponseDto login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Access Token 및 Refresh Token 생성
        String accessToken = jwtProvider.generateAccessToken(email, 1000 * 60 * 30); // 30분 유효
        String refreshToken = jwtProvider.generateRefreshToken(email); // 7일 유효

        // Refresh Token 저장
        member.setRefreshToken(refreshToken);
        memberRepository.save(member);

        return new TokenResponseDto(accessToken, refreshToken);
    }

    // Access Token 갱신
    public String refreshAccessToken(String refreshToken) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid or expired refresh token");
        }

        String email = jwtProvider.getEmailFromToken(refreshToken);

        // 데이터베이스에서 Refresh Token 검증
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!refreshToken.equals(member.getRefreshToken())) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        // 새로운 Access Token 발급
        return jwtProvider.generateAccessToken(email, 1000 * 60 * 30);
    }
}