package org.ms.library.client.feign;

import org.ms.library.client.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient (name = "user", path = "/api/user")
public interface FeignClients {


    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO);


}
