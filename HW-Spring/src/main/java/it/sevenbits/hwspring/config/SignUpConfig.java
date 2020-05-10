package it.sevenbits.hwspring.config;

import it.sevenbits.hwspring.web.controllers.auth.HeaderSignUpController;
import it.sevenbits.hwspring.web.service.signup.SignUpService;
import it.sevenbits.hwspring.web.security.JwtTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SignUpConfig {

    /**
     * Provide certain controller for signing up
     * @param signUpService is a service which provides sign up
     * @param jwtTokenService is a service for work with JWT token
     * @return configured sign up controller
     */
    @Bean
    public Object signUpController(final SignUpService signUpService, final JwtTokenService jwtTokenService) {
        return new HeaderSignUpController(signUpService, jwtTokenService);
    }
}
