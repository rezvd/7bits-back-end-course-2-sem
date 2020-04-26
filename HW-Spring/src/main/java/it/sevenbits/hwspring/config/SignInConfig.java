package it.sevenbits.hwspring.config;

import it.sevenbits.hwspring.core.service.signin.SignInService;
import it.sevenbits.hwspring.web.controllers.auth.CookieSignInController;
import it.sevenbits.hwspring.web.security.JwtTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SignInConfig {

    @Bean
    public Object signInController(final SignInService signInService, final JwtTokenService jwtTokenService) {
//        return new BodyLoginController(loginService, jwtTokenService);
        return new CookieSignInController(signInService, jwtTokenService);
    }
}
