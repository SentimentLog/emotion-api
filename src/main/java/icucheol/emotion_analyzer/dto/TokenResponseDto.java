package icucheol.emotion_analyzer.dto;


import lombok.Data;
import lombok.AllArgsConstructor;


@Data
@AllArgsConstructor
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;
}
