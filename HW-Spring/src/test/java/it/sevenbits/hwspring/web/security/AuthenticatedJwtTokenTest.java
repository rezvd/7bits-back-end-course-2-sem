package it.sevenbits.hwspring.web.security;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AuthenticatedJwtTokenTest {

    @Test
    public void testAuthenticatedJwtToken() {
        String subject = "user";
        List<GrantedAuthority> auths = new ArrayList<>();
        auths.add(new SimpleGrantedAuthority("USER"));

        AuthenticatedJwtToken token = new AuthenticatedJwtToken(subject, auths);

        assertEquals(token.getPrincipal(), subject);
        assertEquals(token.getAuthorities(), auths);
    }

}