package it.sevenbits.hwspring.core.repository.users;


import it.sevenbits.hwspring.core.model.User;

import java.util.List;

public interface UsersRepository {

    /**
     * Searches user in repository by username
     * @param username is a username, chosen by user
     * @return User, if user with such username exists, otherwise null
     */
    User findByUserName(String username);

    /**
     * Searches user in repository by ID
     * @param id is an ID of user
     * @return User, if user with such ID exists, otherwise null
     */
    User findByID(String id);

    /**
     * Make a list with all users in the repository
     * @return list with all users
     */
    List<User> findAllUsers();

    /**
     * Puts new user to the repository
     * @param username is a username, chosen by user
     * @param password is a password of User
     * @param authorities is a list of user's roles
     * @return created user
     */
    User create(String username, String password, List<String> authorities);

}
