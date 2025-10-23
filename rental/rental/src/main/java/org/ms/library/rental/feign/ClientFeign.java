package org.ms.library.rental.feign;

import org.ms.library.rental.dto.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CLIENT-SERVICE", path = "/api/client")
public interface ClientFeign {

    @GetMapping("/{id}")
    ResponseEntity<ClientDTO> findById(@PathVariable("id") Long id);

}
