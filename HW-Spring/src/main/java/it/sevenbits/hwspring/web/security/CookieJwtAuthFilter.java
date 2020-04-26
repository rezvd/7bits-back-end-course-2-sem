package it.sevenbits.hwspring.web.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Filter to take JwtToken from the request header.
 */
public class CookieJwtAuthFilter extends JwtAuthFilter {

    /**
     * Constructor for CookieJwtAuthFilter
     * @param matcher for filtering requests
     */
    public CookieJwtAuthFilter(final RequestMatcher matcher) {
        super(matcher);
    }

    @Override
    protected String takeToken(final HttpServletRequest request) throws AuthenticationException {
        Cookie cookie = WebUtils.getCookie(request, "accessToken");
        if (cookie != null) {
            return cookie.getValue();
        } else {
            throw new JwtAuthenticationException("Invalid 'accessToken' cookie: " + cookie);
        }
    }

}
