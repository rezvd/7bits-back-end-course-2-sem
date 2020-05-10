package it.sevenbits.hwspring.web.controllers;

import it.sevenbits.hwspring.core.model.User;
import it.sevenbits.hwspring.core.repository.users.UsersRepository;
import it.sevenbits.hwspring.core.service.validation.UUIDValidator;
import it.sevenbits.hwspring.web.controllers.exception.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

/**
 * Controller for work with /users
 */
@Controller
@RequestMapping("/users")
public class UsersController {

    private final UsersRepository usersRepository;

    /**
     * Construction for UsersController
     * @param usersRepository is a repository which keep all information about users
     */
    public UsersController(final UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(usersRepository.findAllUsers(true));
    }

    /**
     * Perform information about certain user
     * @param id is an ID of user
     * @return ResponseEntity with user information
     * @throws ValidationException if user id is not UUID
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<User> getUserInfo(final @PathVariable("id") String id) throws ValidationException {
        if (!UUIDValidator.isValid(id)) {
            throw new ValidationException(String.format("ID \"%s\" is not valid", id));
        }
        return Optional
                .ofNullable(usersRepository.findByID(id))
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
