package it.sevenbits.hwspring.web.controllers;

import it.sevenbits.hwspring.core.model.User;
import it.sevenbits.hwspring.core.service.WhoamiService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller to display the current user.
 */
@Controller
@RequestMapping("/whoami")
public class WhoamiController {

    private WhoamiService whoamiService;

    public WhoamiController(final WhoamiService whoamiService) {
        this.whoamiService = whoamiService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<User> get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = whoamiService.getUserFromAuth(authentication);
        return ResponseEntity.ok(user);
    }
}
