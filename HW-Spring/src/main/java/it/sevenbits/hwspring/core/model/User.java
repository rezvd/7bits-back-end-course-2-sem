package it.sevenbits.hwspring.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class User {

    @JsonProperty("username")
    private final String username;

    @JsonProperty("authorities")
    private final List<String> authorities;

    @JsonIgnore
    private final String password;

    public User(String username, String password, List<String> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @JsonCreator
    public User( String username,  List<String> authorities) {
        this.username = username;
        this.password = null;
        this.authorities = authorities;
    }

    public User(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        password = null;

        authorities = new ArrayList<>();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            authorities.add(authority.getAuthority());
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getAuthorities() {
        return authorities;
    }
}
