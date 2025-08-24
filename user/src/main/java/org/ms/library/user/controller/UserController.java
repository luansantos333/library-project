package org.ms.library.user.controller;

import jakarta.validation.Valid;
import org.ms.library.user.dto.UserDTO;
import org.ms.library.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<UserDTO> findUserRoleByUsername(@RequestParam(required = true, name = "username") String username) {

        UserDTO userByUsernameAndPassword = userService.findUserRoleByUsername(username);
        return userByUsernameAndPassword == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(userByUsernameAndPassword);

    }

    @GetMapping("/{UUID}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable String uuid) {

        UserDTO userByUUID = userService.findUserByUUID(UUID.fromString(uuid));

        return userByUUID == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(userByUUID);

    }


    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {

        UserDTO user = userService.createUser(userDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{UUID}").buildAndExpand(userDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }


}
