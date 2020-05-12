package it.sevenbits.hwspring.web.service;

import it.sevenbits.hwspring.core.model.User;
import it.sevenbits.hwspring.core.repository.users.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Service for giving information about current user
 */
@Service
public class WhoamiService {

    private final UsersRepository users;

    /**
     * Constructor for WhoamiService
     * @param users is a user repository
     */
    public WhoamiService(final UsersRepository users) {
        this.users = users;
    }

    /**
     * Extract information about current user from SecurityContextHolder
     * @return current user
     */
    public User getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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
