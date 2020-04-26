package it.sevenbits.hwspring.core.service;

import it.sevenbits.hwspring.core.model.User;
import it.sevenbits.hwspring.core.repository.users.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class WhoamiService {

    private final UsersRepository users;

    public WhoamiService(UsersRepository users) {
        this.users = users;
    }

    public User getUserFromAuth(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String id = "";
        if (principal instanceof UserDetails) {
            id = ((UserDetails) principal).getUsername();
        } else {
            id = principal.toString();
        }
        return users.findByID(id);
    }
}
