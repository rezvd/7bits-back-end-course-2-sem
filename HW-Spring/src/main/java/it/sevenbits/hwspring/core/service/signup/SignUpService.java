package it.sevenbits.hwspring.core.service.signup;

import it.sevenbits.hwspring.core.model.User;
import it.sevenbits.hwspring.core.repository.users.UsersRepository;
import it.sevenbits.hwspring.web.model.auth.SignUp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SignUpService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UsersRepository users;
    private final PasswordEncoder passwordEncoder;

    public SignUpService(UsersRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUp(SignUp signUp) {
        if (signUp.getPassword().isEmpty()) {
            throw new SignUpFailedException("Empty password");
        }

        User user = users.findByUserName(signUp.getUsername());

        if (user != null) {
            throw new SignUpFailedException("User '" + signUp.getUsername() + "' already exist");
        }

        List<String> authorities = new ArrayList<>();
        authorities.add("USER");
        user = users.create(signUp.getUsername(), signUp.getPassword(), authorities);


        return user;
    }

}
