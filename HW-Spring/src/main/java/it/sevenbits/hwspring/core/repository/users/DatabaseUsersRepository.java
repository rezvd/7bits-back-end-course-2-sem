package it.sevenbits.hwspring.core.repository.users;

import it.sevenbits.hwspring.core.model.Task;
import it.sevenbits.hwspring.core.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
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
    private final String ID = "id";
    private final String USERNAME = "username";
    private final String PASSWORD = "password";

    public DatabaseUsersRepository(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public User findByUserName(String username) {
        Map<String, Object> rawUser;

        try {
            rawUser = jdbcOperations.queryForMap(
                    "SELECT id, username, password FROM users u" +
                            " WHERE u.enabled = true AND u.username = ?",
                    username
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }

        String id = String.valueOf(rawUser.get(ID));

        List<String> authorities = new ArrayList<>();
        jdbcOperations.query(
                "SELECT user_id, authority FROM authorities" +
                        " WHERE user_id = ?",
                resultSet -> {
                    String authority = resultSet.getString(AUTHORITY);
                    authorities.add(authority);
                },
                id
        );

        String password = String.valueOf(rawUser.get(PASSWORD));
        return new User(id, username, password, authorities);
    }


    public User findByID(String id) {
        Map<String, Object> rawUser;

        try {
            rawUser = jdbcOperations.queryForMap(
                    "SELECT id, username, password FROM users u" +
                            " WHERE u.enabled = true AND u.id = ?",
                    id
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }

        List<String> authorities = new ArrayList<>();
        jdbcOperations.query(
                "SELECT user_id, authority FROM authorities" +
                        " WHERE user_id = ?",
                resultSet -> {
                    String authority = resultSet.getString(AUTHORITY);
                    authorities.add(authority);
                },
                id
        );

        String username = String.valueOf(rawUser.get(USERNAME));
        String password = String.valueOf(rawUser.get(PASSWORD));
        return new User(id, username, password, authorities);

    }

    public List<User> findAll() {
        ArrayList<User> users = new ArrayList<>();
        for (Map<String, Object> row : jdbcOperations.queryForList(
                "(SELECT * FROM users u WHERE u.enabled = true)")) {
            String username = String.valueOf(row.get(USERNAME));
            String id = String.valueOf(row.get(ID));
            String password = String.valueOf(row.get(PASSWORD));
            List<String> auths = jdbcOperations.queryForList("(SELECT authority FROM  authorities" +
                                                                    " WHERE user_id = ?)",
                    String.class,
                    id);
            users.add(new User(id, username, password, auths));
        }
        return users;
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
