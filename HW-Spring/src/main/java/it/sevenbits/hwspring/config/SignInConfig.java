package it.sevenbits.hwspring.config;

import it.sevenbits.hwspring.web.controllers.auth.HeaderSignInController;
import it.sevenbits.hwspring.web.service.signin.SignInService;
import it.sevenbits.hwspring.web.security.JwtTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SignInConfig {

    /**
     * Provide certain controller for signing in
     * @param signInService is a service which provides sign in
     * @param jwtTokenService is a service for work with JWT token
     * @return configured sign in controller
     */
    @Bean
    public Object signInController(final SignInService signInService, final JwtTokenService jwtTokenService) {
        return new HeaderSignInController(signInService, jwtTokenService);
    }
}
