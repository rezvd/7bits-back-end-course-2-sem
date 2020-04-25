package it.sevenbits.hwspring.web.controllers;

import it.sevenbits.hwspring.core.model.User;
import it.sevenbits.hwspring.core.service.login.LoginService;
import it.sevenbits.hwspring.web.model.Login;
import it.sevenbits.hwspring.web.model.Token;
import it.sevenbits.hwspring.web.security.JwtTokenService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Performs login action.
 */
@RequestMapping("/signin")
public class BodyLoginController {

    private final LoginService loginService;
    private final JwtTokenService tokenService;

    public BodyLoginController(final LoginService loginService, final JwtTokenService tokenService) {
        this.loginService = loginService;
        this.tokenService = tokenService;
    }

    @PostMapping
    @ResponseBody
    public Token create(@RequestBody Login login) {
        User user = loginService.login(login);
        String token = tokenService.createToken(user);
        return new Token(token);
    }
}
