package it.sevenbits.hwspring.config;

import it.sevenbits.hwspring.core.service.signup.SignUpService;
import it.sevenbits.hwspring.web.controllers.CookieSignUpController;
import it.sevenbits.hwspring.web.security.JwtTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SignUpConfig {

    @Bean
    public Object signUpController(final SignUpService signUpService, final JwtTokenService jwtTokenService) {
        return new CookieSignUpController(signUpService, jwtTokenService);
    }
}
