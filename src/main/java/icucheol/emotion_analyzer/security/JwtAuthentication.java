package icucheol.emotion_analyzer.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthentication extends AbstractAuthenticationToken {

    private final String principal; // 이메일 또는 사용자 식별자
    private Object credentials;

    public JwtAuthentication(String principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(true); // JWT는 이미 검증된 토큰이므로 true 설정
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}