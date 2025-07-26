package org.ms.library.rental.feign;

import org.ms.library.rental.dto.BookCategoriesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(name = "CATALOG-SERVICE", path = "/api/catalog/book")
public interface CatalogFeign {
    @GetMapping("/{id}")
    ResponseEntity<BookCategoriesDTO> findOneBook(@PathVariable(name = "id") Long id);

    @GetMapping("/by-ids")
    ResponseEntity<Set<BookCategoriesDTO>> findBooksByIds(@RequestParam(name = "ids", required = true) Set<Long> ids);
    @PutMapping("/stock/{id}")
    ResponseEntity<BookCategoriesDTO> changeStockQuantity(@PathVariable(name = "id") Long id, @RequestParam(required = true, name = "amount") Integer amount, @RequestParam (required = true, name = "operation", defaultValue = "increase") String operation);
}
