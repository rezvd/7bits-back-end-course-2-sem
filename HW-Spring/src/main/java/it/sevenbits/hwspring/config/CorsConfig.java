package it.sevenbits.hwspring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.regex.Pattern;

/**
 * Configures preflight requests
 */
@Configuration
public class CorsConfig {

    @Value("${corsHeaders.allowOrigins:*}")
    private String allowOrigins;

    @Value("${corsHeaders.allowCredentials:true}")
    private final boolean allowCredentials = true;

    @Value("${corsHeaders.allowMethods:GET,POST,DELETE,PATCH,OPTIONS}")
    private String allowMethods;

    @Value("${corsHeaders.allowHeaders:Authorization,Origin,Accept,Key,DNT,Keep-Alive,User-Agent," +
            "X-Requested-With,If-Modified-Since,Cache-Control,Content-Type}")
    private String allowHeaders;

    private final Pattern delimiter = Pattern.compile("[,\\s]+");

    /**
     * Allows preflight requests
     * @return configures WebMvcConfigurer
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(delimiter.split(allowOrigins))
                        .allowCredentials(allowCredentials)
                        .allowedMethods(delimiter.split(allowMethods))
                        .allowedHeaders(delimiter.split(allowHeaders));
            }
        };
    }
}
