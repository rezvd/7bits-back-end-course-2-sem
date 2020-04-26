package it.sevenbits.hwspring.web.controllers.auth;

import it.sevenbits.hwspring.core.model.User;
import it.sevenbits.hwspring.core.service.signin.SignInService;
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
 * Performs login action.
 */
@RequestMapping("/signin")
public class CookieSignInController {

    private final SignInService signInService;
    private final JwtTokenService tokenService;

    public CookieSignInController(final SignInService signInService, final JwtTokenService tokenService) {
        this.signInService = signInService;
        this.tokenService = tokenService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity create(@RequestBody SignIn signIn, HttpServletResponse response) {
        User user = signInService.signIn(signIn);
        String token = tokenService.createToken(user);

        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) (tokenService.getTokenExpiredIn().toMillis() / 1000));
        response.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }

}
