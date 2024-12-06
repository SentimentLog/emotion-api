package icucheol.emotion_analyzer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {
    @Value("${JWT_SECRET_KEY}")
    private String secretKey;

    public String getSecretKey() {
        return secretKey;
    }
}


