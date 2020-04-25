package it.sevenbits.hwspring.web.controllers;

import it.sevenbits.hwspring.core.model.User;
import it.sevenbits.hwspring.core.service.signin.SignInService;
import it.sevenbits.hwspring.web.model.SignIn;
import it.sevenbits.hwspring.web.model.Token;
import it.sevenbits.hwspring.web.security.JwtTokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Performs login action.
 */
@RequestMapping("/signin")
public class BodySignInController {

    private final SignInService signInService;
    private final JwtTokenService tokenService;

    public BodySignInController(final SignInService signInService, final JwtTokenService tokenService) {
        this.signInService = signInService;
        this.tokenService = tokenService;
    }

    @PostMapping
    @ResponseBody
    public Token create(@RequestBody SignIn signIn) {
        User user = signInService.signIn(signIn);
        String token = tokenService.createToken(user);
        return new Token(token);
    }
}
