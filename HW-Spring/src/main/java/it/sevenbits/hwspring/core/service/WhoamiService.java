package it.sevenbits.hwspring.core.service;

import it.sevenbits.hwspring.core.model.User;
import it.sevenbits.hwspring.core.repository.users.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class WhoamiService {

    private final UsersRepository users;

    public WhoamiService(UsersRepository users) {
        this.users = users;
    }

    public User getUserFromAuth(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return users.findByUserName(username);
    }
}
