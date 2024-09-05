package uz.result.rmcdeluxe.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig
{

    @Bean
    public OpenAPI customOpenAPI()
    {
        return new OpenAPI()
                .info(new Info().title("rmc-de-luxe API")
                        .version("1.0")
                        .description("Backend sila karoche \uD83D\uDCAA \uD83D\uDCAA\uD83C\uDFFF"))
                .servers(List.of(
                        new Server().url("http://127.0.0.1:8150").description("Localhost"),
                        new Server().url("http://213.230.91.55:8150").description("Server with IP"),
                        new Server().url("https://rmc-de-luxe.uz/api").description("Server with domain")
                ));
    }
}
