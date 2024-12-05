package icucheol.emotion_analyzer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequestDto {
    private String username; // 사용자 이름
    private String email;    // 이메일
    private String password; // 비밀번호
}