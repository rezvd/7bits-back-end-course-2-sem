package it.sevenbits.hwspring.core.repository.users;


import it.sevenbits.hwspring.core.model.User;

import java.util.List;

public interface UsersRepository {

    User findByUserName(String username);

    User findByID(String id);

    List<User> findAll();

}
