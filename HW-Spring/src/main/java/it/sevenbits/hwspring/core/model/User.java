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

    /**
     * Constructor for User
     * @param id is a unique user ID
     * @param username is a username, chosen by user
     * @param password is a password of user
     * @param authorities is a list of user's roles
     */
    public User(final String id, final String username, final String password, final List<String> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * JSON constructor for User
     * @param id is a unique user ID
     * @param username is a username, chosen by user
     * @param authorities is a list of user's roles
     */
    @JsonCreator
    public User(final String id, final String username,  final List<String> authorities) {
        this.id = id;
        this.username = username;
        this.password = null;
        this.authorities = authorities;
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

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id.equals(user.id) &&
                username.equals(user.username) &&
                authorities.equals(user.authorities) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, authorities, password);
    }
}
