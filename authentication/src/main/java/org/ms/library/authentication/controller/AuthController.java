package org.ms.library.authentication.controller;

import org.ms.library.authentication.dto.UserDTO;
import org.ms.library.authentication.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/authentication")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public ResponseEntity<UserDetails> findByEmail (@RequestParam(required = true, name = "email") String email) {

        UserDetails userDetails = userService.loadUserByUsername(email);

        return ResponseEntity.ok(userDetails);

    }


}
