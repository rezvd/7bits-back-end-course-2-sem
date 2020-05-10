package it.sevenbits.hwspring.core.repository.users;

import it.sevenbits.hwspring.core.model.User;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static java.lang.Boolean.TRUE;

/**
 * Repository to list all users.
 */
public class UsersRepositoryDB implements UsersRepository {
    private final JdbcOperations jdbcOperations;
    private static final String AUTHORITY = "authority";
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ENABLED = "enabled";

    /**
     * Conctructor for UsersRepositoryDB
     * @param jdbcOperations provides work with DB
     */
    public UsersRepositoryDB(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    /**
     * Searches user in repository by username
     * @param username is a username, chosen by user
     * @return User, if user with such username exists, otherwise null
     */
    public User findByUserName(final String username) {
        Map<String, Object> rawUser;

        try {
            rawUser = jdbcOperations.queryForMap(
                    "SELECT id, username, password, enabled FROM users" +
                            " WHERE enabled = true AND username = ?",
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
        boolean enabled = (Boolean) rawUser.get(ENABLED);
        return new User(id, username, password, authorities, enabled);
    }

    /**
     * Searches user in repository by ID
     * @param id is an ID of user
     * @return User, if user with such ID exists, otherwise null
     */
    public User findByID(final String id) {
        Map<String, Object> rawUser;

        try {
            rawUser = jdbcOperations.queryForMap(
                    "SELECT id, username, password, enabled FROM users" +
                            " WHERE id = ?",
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
        boolean enabled = (Boolean) rawUser.get(ENABLED);
        return new User(id, username, password, authorities, enabled);

    }

    /**
     * Make a list with all users in the repository
     * @param enabled is a param for searching users
     * @return list with all users
     */
    public List<User> findAllUsers(final boolean enabled) {
        ArrayList<User> users = new ArrayList<>();
        for (Map<String, Object> row : jdbcOperations.queryForList(
                "(SELECT * FROM users u WHERE u.enabled = ?)", enabled)) {
            String username = String.valueOf(row.get(USERNAME));
            String id = String.valueOf(row.get(ID));
            String password = String.valueOf(row.get(PASSWORD));
            List<String> auths = jdbcOperations.queryForList("(SELECT authority FROM authorities" +
                                                                    " WHERE user_id = ?)",
                    String.class,
                    id);
            users.add(new User(id, username, password, auths, enabled));
        }
        return users;
    }


    /**
     * Puts new user to the repository
     * @param username is a username, chosen by user
     * @param password is a password of User
     * @param authorities is a list of user's roles
     * @return created user
     */
    @Override
    public User create(final String username, final String password, final List<String> authorities) {
        String id = getNextID();
        jdbcOperations.update(
                "INSERT INTO users (id, username, password, enabled) VALUES (?, ?, ?, ?)",
                id, username, password, TRUE
        );
        for (String a: authorities) {
            jdbcOperations.update(
                    "INSERT INTO authorities (user_id, authority) VALUES (?, ?)",
                    id, a
            );
        }
        return new User(id, username, password, authorities, TRUE);
    }

    @Override
    public void update(final User newUser, final User previousUser) {
        jdbcOperations.update(
                "UPDATE users SET enabled = COALESCE(?, ?) WHERE id = ?",
                newUser.isEnabled(), previousUser.isEnabled(),
                newUser.getId()
        );
    }


    /**
     * Generates new UUID
     * @return new random UUID
     */
    private String getNextID() {
        return UUID.randomUUID().toString();
    }

}
