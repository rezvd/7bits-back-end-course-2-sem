package it.sevenbits.hwspring.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

public class User {

    @JsonProperty("id")
    private final String id;

    @JsonProperty("username")
    private final String username;

    @JsonProperty("authorities")
    private final List<String> authorities;

    @JsonIgnore
    private final String password;

    @JsonIgnore
    private final boolean enabled;

    /**
     * Constructor for User
     * @param id is a unique user ID
     * @param username is a username, chosen by user
     * @param password is a password of user
     * @param authorities is a list of user's roles
     */
    public User(final String id,
                final String username,
                final String password,
                final List<String> authorities,
                final boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
    }

    /**
     * JSON constructor for User
     * @param id is a unique user ID
     * @param username is a username, chosen by user
     * @param authorities is a list of user's roles
     */
    @JsonCreator
    public User(@JsonProperty("id") final String id,
                @JsonProperty("username") final String username,
                @JsonProperty("authorities") final List<String> authorities,
                @JsonProperty("enabled") final boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = null;
        this.authorities = authorities;
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public String getId() {
        return id;
    }

    @JsonIgnore
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return enabled == user.enabled &&
                id.equals(user.id) &&
                username.equals(user.username) &&
                authorities.equals(user.authorities) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, authorities, password, enabled);
    }
}
