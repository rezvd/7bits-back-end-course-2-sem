package it.sevenbits.hwspring.web.service.signup;

import it.sevenbits.hwspring.core.model.User;
import it.sevenbits.hwspring.core.repository.users.UsersRepository;
import it.sevenbits.hwspring.web.model.auth.SignUp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for signing users up
 */
@Service
public class SignUpService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UsersRepository users;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for SignUpService
     * @param users is a user repository
     * @param passwordEncoder is an encoder for encoding passwords
     */
    public SignUpService(final UsersRepository users, final PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Performs signing up
     * @param signUp contains information about user needed to sign up
     * @return created user
     */
    public User signUp(final SignUp signUp) {
        if (signUp.getPassword().isEmpty()) {
            throw new SignUpFailedException("Empty password");
        }

        User user = users.findByUserName(signUp.getUsername());

        if (user != null) {
            throw new SignUpFailedException("User '" + signUp.getUsername() + "' already exist");
        }

        List<String> authorities = new ArrayList<>();
        authorities.add("USER");
        user = users.create(signUp.getUsername(), passwordEncoder.encode(signUp.getPassword()), authorities);
        return user;
    }

}
