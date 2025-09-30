package org.ms.library.rental.feign;

import org.ms.library.rental.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE", path = "/api/user")
public interface UserFeign {

    @GetMapping("/{uuid}")
    ResponseEntity<UserDTO> findUserById(@PathVariable("uuid") String uuid);
}
