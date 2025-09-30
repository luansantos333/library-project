package org.ms.library.client.feign;

import org.ms.library.client.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Component
@FeignClient (name = "user", path = "/api/user")
public interface FeignClients {


    @PostMapping
    ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO);

    @GetMapping("/{UUID}")
    ResponseEntity<UserDTO> findUserById(@PathVariable String uuid);


}
