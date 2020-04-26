package it.sevenbits.hwspring.web.controllers.auth;

import it.sevenbits.hwspring.core.model.User;
import it.sevenbits.hwspring.web.service.signin.SignInService;
import it.sevenbits.hwspring.web.model.auth.SignIn;
import it.sevenbits.hwspring.web.model.auth.Token;
import it.sevenbits.hwspring.web.security.JwtTokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Performs sign in action.
 */
@RequestMapping("/signin")
public class BodySignInController {

    private final SignInService signInService;
    private final JwtTokenService tokenService;

    /**
     * Constructor for BodySignInController
     * @param signInService is a service which provides log in
     * @param tokenService is a service for work with token
     */
    public BodySignInController(final SignInService signInService, final JwtTokenService tokenService) {
        this.signInService = signInService;
        this.tokenService = tokenService;
    }

    /**
     * Creates token with user information
     * @param signIn contains user information needed to sign in
     * @return token with user information
     */
    @PostMapping
    @ResponseBody
    public Token create(final @RequestBody SignIn signIn) {
        User user = signInService.signIn(signIn);
        String token = tokenService.createToken(user);
        return new Token(token);
    }
}
