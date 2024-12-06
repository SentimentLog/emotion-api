package icucheol.emotion_analyzer.controller;

import icucheol.emotion_analyzer.dto.LoginRequestDto;
import icucheol.emotion_analyzer.dto.TokenResponseDto;
import icucheol.emotion_analyzer.dto.RefreshTokenRequestDto;
import icucheol.emotion_analyzer.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        TokenResponseDto tokens = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(tokens);
    }

    // Access Token 갱신 API
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshAccessToken(@RequestBody RefreshTokenRequestDto refreshTokenRequest) {
        String newAccessToken = authService.refreshAccessToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.ok(newAccessToken);
    }
}