package icucheol.emotion_analyzer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDto {
    private Long id;         // 회원 ID
    private String username; // 사용자 이름
    private String email;    // 이메일

    public MemberResponseDto(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}