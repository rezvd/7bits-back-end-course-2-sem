package it.sevenbits.hwspring.web.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Authorization which holds subject and roles/authorities from the JWT token.
 */
class AuthenticatedJwtToken extends AbstractAuthenticationToken {

    private final String subject;

    /**
     * Constructor for AuthenticatedJwtToken
     * @param subject is ID of authenticated user
     * @param authorities is list of user's roles
     */
    AuthenticatedJwtToken(final String subject, final Collection<GrantedAuthority> authorities) {
        super(authorities);
        this.subject = subject;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return subject;
    }

}
