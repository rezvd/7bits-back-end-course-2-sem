package it.sevenbits.hwspring.web.controllers.auth;

import it.sevenbits.hwspring.core.model.User;
import it.sevenbits.hwspring.web.service.signin.SignInService;
import it.sevenbits.hwspring.web.model.auth.SignIn;
import it.sevenbits.hwspring.web.security.JwtTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Performs sign in action.
 */
//@RequestMapping("/signin")
public class CookieSignInController {

    private final SignInService signInService;
    private final JwtTokenService tokenService;

    /**
     * Constructor for CookieSignInController
     * @param signInService is a service which provides log in
     * @param tokenService is a service for work with token
     */
    public CookieSignInController(final SignInService signInService, final JwtTokenService tokenService) {
        this.signInService = signInService;
        this.tokenService = tokenService;
    }

    /**
     * Creates token with user information
     * @param signIn contains user information needed to sign in
     * @param response is a response to the request
     * @return token with user information
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity create(final @RequestBody SignIn signIn, final HttpServletResponse response) {
        User user = signInService.signIn(signIn);
        String token = tokenService.createToken(user);
        final int millis = 1000;

        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) (tokenService.getTokenExpiredIn().toMillis() / millis));
        response.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }

}
