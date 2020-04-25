package it.sevenbits.hwspring.core.repository.users;

import it.sevenbits.hwspring.core.model.Task;
import it.sevenbits.hwspring.core.model.User;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Boolean.TRUE;

/**
 * Repository to list all users.
 */
public class DatabaseUsersRepository implements UsersRepository {
    private final JdbcOperations jdbcOperations;
    private final String AUTHORITY = "authority";
    private final String USERNAME = "username";
    private final String PASSWORD = "password";

    public DatabaseUsersRepository(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public User findByUserName(String username) {
        Map<String, Object> rawUser;

        try {
            rawUser = jdbcOperations.queryForMap(
                    "SELECT username, password FROM users u" +
                            " WHERE u.enabled = true AND u.username = ?",
                    username
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }

        List<String> authorities = new ArrayList<>();
        jdbcOperations.query(
                "SELECT username, authority FROM authorities" +
                        " WHERE username = ?",
                resultSet -> {
                    String authority = resultSet.getString(AUTHORITY);
                    authorities.add(authority);
                },
                username
        );

        String password = String.valueOf(rawUser.get(PASSWORD));
        return new User(username, password, authorities);
    }

    public List<User> findAll() {
        HashMap<String, User> users = new HashMap<>();

        for (Map<String, Object> row : jdbcOperations.queryForList(
                "SELECT username, authority FROM authorities a" +
                        " WHERE EXISTS" +
                        " (SELECT * FROM users u WHERE" +
                        " u.username = a.username AND u.enabled = true)")) {

            String username = String.valueOf(row.get(USERNAME));
            String newRole = String.valueOf(row.get(AUTHORITY));
            User user = users.computeIfAbsent(username, name -> new User(name, new ArrayList<>()));
            List<String> roles = user.getAuthorities();
            roles.add(newRole);

        }

        return new ArrayList<>(users.values());
    }

    @Override
    public User create(String username, String password, List<String> authorities) {
        jdbcOperations.update(
                "INSERT INTO users (username, password, enabled) VALUES (?, ?, ?)",
                username, password, TRUE
        );
        jdbcOperations.update(
                "INSERT INTO authorities (username, authority) VALUES (?, ?)",
                username, "USER"
        );
        return new User(username, password, authorities);
    }

}