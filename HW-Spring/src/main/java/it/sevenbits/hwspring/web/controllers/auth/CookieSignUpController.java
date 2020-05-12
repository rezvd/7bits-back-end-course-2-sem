package it.sevenbits.hwspring.web.controllers.auth;

import it.sevenbits.hwspring.core.model.User;
import it.sevenbits.hwspring.web.service.signup.SignUpService;
import it.sevenbits.hwspring.web.model.auth.SignUp;
import it.sevenbits.hwspring.web.security.JwtTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Performs sign up action.
 */
@RequestMapping("/signup")
public class CookieSignUpController {

    private final SignUpService signUpService;
    private final JwtTokenService tokenService;

    /**
     * Constructor for CookieSignUpController
     * @param signUpService is a service which provides sign up
     * @param tokenService is a service for work with token
     */
    public CookieSignUpController(final SignUpService signUpService, final JwtTokenService tokenService) {
        this.signUpService = signUpService;
        this.tokenService = tokenService;
    }

    /**
     * Creates token with user information
     * @param signUp contains user information needed to sign up
     * @param response is a response to the request
     * @return token with user information
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity create(@RequestBody final SignUp signUp, final HttpServletResponse response) {
        User user = signUpService.signUp(signUp);
        String token = tokenService.createToken(user);
        final int millis = 1000;

        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) (tokenService.getTokenExpiredIn().toMillis() / millis));
        response.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }

}
