package it.sevenbits.hwspring.web.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sevenbits.hwspring.core.model.User;
import it.sevenbits.hwspring.core.repository.users.UsersRepository;
import it.sevenbits.hwspring.core.service.validation.UUIDValidator;
import it.sevenbits.hwspring.web.controllers.exception.NotFoundException;
import it.sevenbits.hwspring.web.controllers.exception.ValidationException;
import it.sevenbits.hwspring.web.model.UserPatchRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     *
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
     *
     * @param id is an ID of user
     * @return ResponseEntity with user information
     * @throws ValidationException if user id is not UUID
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<User> getUserInfo(@PathVariable("id") final String id) throws ValidationException {
        if (!UUIDValidator.isValid(id)) {
            throw new ValidationException(String.format("ID \"%s\" is not valid", id));
        }
        return Optional
                .ofNullable(usersRepository.findByID(id))
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Handle PATCH request to certain task
     *
     * @param id      is ID of needed user
     * @param patchRequest is a model which contains all needed data to update user
     * @return http status
     * @throws NotFoundException   if user with such ID doesn't exist
     * @throws ValidationException if ID is not valid
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity update(@PathVariable final String id,
                                 @RequestBody @JsonProperty("enabled") final UserPatchRequest patchRequest)
            throws NotFoundException, ValidationException {
        if (!UUIDValidator.isValid(id)) {
            throw new ValidationException(String.format("ID \"%s\" is not valid", id));
        }
        if (patchRequest.getEnabled() == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        User previousUser = usersRepository.findByID(id);
        if (previousUser == null) {
            throw new NotFoundException(String.format("User with id \"%s\" wasn't found", id));
        }
        if (patchRequest.getEnabled() != previousUser.isEnabled()) {
            usersRepository.update(new User(id, null, null, patchRequest.getEnabled()),
                    previousUser);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
