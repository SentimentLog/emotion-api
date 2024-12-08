package icucheol.emotion_analyzer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                .title("메인 API서버 전용 스웨거")
                .version("1.0.0")
                .description("감정분석 일기를 위한 API 문서")
                .contact(new Contact()
                        .name("UICHEOL_HWANG")
                        .email("icuchoel@gmail.com")
                        .url("hhttps://github.com/UICHEOL-HWANG")
                )
            );
    }
}
