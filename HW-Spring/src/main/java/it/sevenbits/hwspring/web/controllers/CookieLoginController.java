package it.sevenbits.hwspring.web.controllers;

import it.sevenbits.hwspring.core.model.User;
import it.sevenbits.hwspring.core.service.login.LoginService;
import it.sevenbits.hwspring.web.model.Login;
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
@RequestMapping("/login")
public class CookieLoginController {

    private final LoginService loginService;
    private final JwtTokenService tokenService;

    public CookieLoginController(final LoginService loginService, final JwtTokenService tokenService) {
        this.loginService = loginService;
        this.tokenService = tokenService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity create(@RequestBody Login login, HttpServletResponse response) {
        User user = loginService.login(login);
        String token = tokenService.createToken(user);

        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int)(tokenService.getTokenExpiredIn().toMillis() / 1000));
        response.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }

}
