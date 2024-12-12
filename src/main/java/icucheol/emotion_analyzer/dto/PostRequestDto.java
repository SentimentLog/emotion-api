package icucheol.emotion_analyzer.dto;

import icucheol.emotion_analyzer.domain.Post;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class PostRequestDto {
    private String title;
    private String content;
}

