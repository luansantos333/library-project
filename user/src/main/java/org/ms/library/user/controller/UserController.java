package org.ms.library.user.controller;

import org.ms.library.user.dto.UserDTO;
import org.ms.library.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<UserDTO> findUserByEmail(@RequestParam(required = true, name = "username") String username) {

        UserDTO userByUsernameAndPassword = userService.findUserByUsernameAndPassword(username);
        return userByUsernameAndPassword == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(userByUsernameAndPassword);

    }


}
