package it.sevenbits.hwspring.core.repository.users;


import it.sevenbits.hwspring.core.model.User;

import java.util.List;

/**
 * Interface for repository to store users information
 */
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
     * @param enabled is a param for searching users
     */
    List<User> findAllUsers(boolean enabled);

    /**
     * Puts new user to the repository
     * @param username is a username, chosen by user
     * @param password is a password of User
     * @param authorities is a list of user's roles
     * @return created user
     */
    User create(String username, String password, List<String> authorities);

    /**
     * Changes existing user according with new data
     * @param newUser is a user to get data from for updating
     * @param previousUser is old user data to keep old information in fields which don't have new data
     */
    void update(User newUser, User previousUser);

}
