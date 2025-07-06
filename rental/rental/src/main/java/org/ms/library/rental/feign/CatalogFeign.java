package org.ms.library.rental.feign;

import org.ms.library.rental.dto.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "CATALOG-SERVICE", path = "/api/catalog/book")
public interface CatalogFeign {
    @GetMapping("/{id}")
    ResponseEntity<BookDTO> findOneBook(@PathVariable(name = "id") Long id);

    @GetMapping("/by-ids")
    ResponseEntity<List<BookDTO>> findBooksByIds(@RequestParam(name = "ids", required = true) List<Long> ids);
}
