package it.sevenbits.hwspring.web.controllers;

import it.sevenbits.hwspring.core.model.User;
import it.sevenbits.hwspring.web.service.WhoamiService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller to display the current user
 */
@Controller
@RequestMapping("/whoami")
public class WhoamiController {

    private WhoamiService whoamiService;

    /**
     * Constructor for WhoamiController
     * @param whoamiService is a service to perform information about current user
     */
    public WhoamiController(final WhoamiService whoamiService) {
        this.whoamiService = whoamiService;
    }

    /**
     * Performs information about current user
     * @return ResponseEntity with information about current user
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<User> get() {
        User user = whoamiService.getUserFromContext();
        return ResponseEntity.ok(user);
    }
}
