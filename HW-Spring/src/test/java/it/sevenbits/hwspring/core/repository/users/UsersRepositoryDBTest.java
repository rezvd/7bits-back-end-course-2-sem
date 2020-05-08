package it.sevenbits.hwspring.core.repository.users;

import it.sevenbits.hwspring.core.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcOperations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;

public class UsersRepositoryDBTest {
    private JdbcOperations mockJdbc;
    private UsersRepositoryDB usersRepository;

    private String username = "username";
    private String id = "userid";
    private String password = "userpassword";
    private List<String> auths = new ArrayList<>();
    List<User> expectedUsers = new ArrayList<>();
    User user;
/*
    @Before
    public void setup() {
        mockJdbc = mock(JdbcOperations.class);
        usersRepository = new UsersRepositoryDB(mockJdbc);
        auths.add("USER");
        user = new User(id, username, password, auths);
        expectedUsers.add(user);
    }

    @Test
    public void testFindByUserName() {
        Map<String, Object> rawUser = new HashMap<>();
        rawUser.put("id", id);
        rawUser.put("password", password);

        User expected = new User(id, username, password, new ArrayList<>());

        when(mockJdbc.queryForMap(anyString(), anyString())).thenReturn(rawUser);

        User actual = usersRepository.findByUserName(username);

        verify(mockJdbc, times(1)).queryForMap(
                eq("SELECT id, username, password FROM users" +
                        " WHERE enabled = true AND username = ?"),
                eq(username)
        );

        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void testFindByID() {
        Map<String, Object> rawUser = new HashMap<>();
        rawUser.put("id", id);
        rawUser.put("username", username);
        rawUser.put("password", password);
        User expected = new User(id, username, password, new ArrayList<>());

        when(mockJdbc.queryForMap(anyString(), anyString())).thenReturn(rawUser);

        User actual = usersRepository.findByID(id);

        verify(mockJdbc, times(1)).queryForMap(
                eq("SELECT id, username, password FROM users" +
                        " WHERE enabled = true AND id = ?"),
                eq(id)
        );

        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void testFindAll() {
        List<Map<String, Object>> users = new ArrayList<>();
        Map<String, Object> rawUser = new HashMap<>();
        rawUser.put("id", id);
        rawUser.put("username", username);
        rawUser.put("password", password);
        users.add(rawUser);

        when(mockJdbc.queryForList(eq("(SELECT * FROM users u WHERE u.enabled = true)"))).thenReturn(users);
        when(mockJdbc.queryForList(eq("(SELECT authority FROM authorities WHERE user_id = ?)"), eq(String.class), eq(id))).thenReturn(auths);

        List<User> actual = usersRepository.findAllUsers();

        verify(mockJdbc, times(1)).queryForList(
                eq("(SELECT * FROM users u WHERE u.enabled = true)"));
        verify(mockJdbc, times(1)).queryForList(
                eq("(SELECT authority FROM authorities WHERE user_id = ?)"), eq(String.class), eq(id));

        Assert.assertTrue(expectedUsers.equals(actual));
    }

    @Test
    public void testCreate() {
        User actual = usersRepository.create(username, password, auths);
        Assert.assertEquals(actual.getUsername(), username);
        Assert.assertEquals(actual.getPassword(), password);
        Assert.assertEquals(actual.getAuthorities(), auths);
    }

 */
}