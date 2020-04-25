package it.sevenbits.hwspring.config;

import it.sevenbits.hwspring.core.service.login.LoginService;
import it.sevenbits.hwspring.web.controllers.BodyLoginController;
import it.sevenbits.hwspring.web.controllers.CookieLoginController;
import it.sevenbits.hwspring.web.security.JwtTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginConfig {

    @Bean
    public Object loginController(final LoginService loginService, final JwtTokenService jwtTokenService) {
//        return new BodyLoginController(loginService, jwtTokenService);
        return new CookieLoginController(loginService, jwtTokenService);
    }
}
