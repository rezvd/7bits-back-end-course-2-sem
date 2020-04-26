package it.sevenbits.hwspring.web.controllers.auth;

import it.sevenbits.hwspring.core.model.User;
import it.sevenbits.hwspring.web.model.auth.SignUp;
import it.sevenbits.hwspring.web.security.JwtTokenService;
import it.sevenbits.hwspring.web.service.signup.SignUpService;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

import static org.mockito.Mockito.*;

public class CookieSignUpControllerTest {

    private SignUpService signUpService;
    private JwtTokenService tokenService;
    private CookieSignUpController cookieSignUpController;

    @Before
    public void setup() {
        signUpService = mock(SignUpService.class);
        tokenService = mock(JwtTokenService.class);
        cookieSignUpController = new CookieSignUpController(signUpService, tokenService);
    }

    @Test
    public void testCreate() {
        String token = "token";
        SignUp signUp = new SignUp("username", "password");
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        when(tokenService.getTokenExpiredIn()).thenReturn(Duration.ofSeconds(30));
        when(tokenService.createToken(any(User.class))).thenReturn(token);
        cookieSignUpController.create(signUp, httpServletResponse);

        verify(signUpService, times(1)).signUp(eq(signUp));
        verify(httpServletResponse, times(1)).addCookie(any(Cookie.class));
    }
}