package it.sevenbits.hwspring.core.service.login;

import it.sevenbits.hwspring.core.model.User;
import it.sevenbits.hwspring.core.repository.users.UsersRepository;
import it.sevenbits.hwspring.web.model.Login;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

    private final UsersRepository users;
    private final PasswordEncoder passwordEncoder;

    public LoginService(UsersRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(Login login) {
        User user = users.findByUserName(login.getUsername());

        if (user == null) {
            throw new LoginFailedException("User '" + login.getUsername() + "' not found");
        }

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new LoginFailedException("Wrong password");
        }
        return new User(user.getUsername(), user.getAuthorities());
    }

}
