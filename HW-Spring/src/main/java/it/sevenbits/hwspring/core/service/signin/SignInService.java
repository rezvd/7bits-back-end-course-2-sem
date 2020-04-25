package it.sevenbits.hwspring.core.service.signin;

import it.sevenbits.hwspring.core.model.User;
import it.sevenbits.hwspring.core.repository.users.UsersRepository;
import it.sevenbits.hwspring.web.model.SignIn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class SignInService {

    private final UsersRepository users;
    private final PasswordEncoder passwordEncoder;

    public SignInService(UsersRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    public User signIn(SignIn signIn) {
        User user = users.findByUserName(signIn.getUsername());

        if (user == null) {
            throw new SignInFailedException("User '" + signIn.getUsername() + "' not found");
        }

        if (!passwordEncoder.matches(signIn.getPassword(), user.getPassword())) {
            throw new SignInFailedException("Wrong password");
        }
        return new User(user.getUsername(), user.getAuthorities());
    }

}
