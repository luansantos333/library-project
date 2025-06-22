package org.ms.library.authentication.feign;

import org.ms.library.authentication.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "user", path = "/api/user")
public interface UserFeignClient {


    @GetMapping
    ResponseEntity<UserDTO> findUserRoleByUsername(@RequestParam(required = true, name = "username") String username);

}
