package icucheol.emotion_analyzer.controller;

import icucheol.emotion_analyzer.dto.LoginRequestDto;
import icucheol.emotion_analyzer.dto.TokenResponseDto;
import icucheol.emotion_analyzer.dto.RefreshTokenRequestDto;
import icucheol.emotion_analyzer.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "로그인 및 인증 관련 API")
public class AuthController {

    private final AuthService authService;

    // 로그인 API
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        TokenResponseDto tokens = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(tokens);
    }

    // Access Token 갱신 API
    @PostMapping("/refresh")
    @Operation(summary="리프레시 토큰", description = "리프레시 토큰을 이용해서 새로운 액세스 토큰 발급")
    public ResponseEntity<String> refreshAccessToken(@RequestBody RefreshTokenRequestDto refreshTokenRequest) {
        String newAccessToken = authService.refreshAccessToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.ok(newAccessToken);
    }
}