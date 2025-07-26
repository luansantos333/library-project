package org.ms.library.rental.controller;

import jakarta.validation.Valid;
import org.ms.library.rental.dto.RentalDTO;
import org.ms.library.rental.dto.RentalsBookCategoriesClientDTO;
import org.ms.library.rental.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/rental")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalsBookCategoriesClientDTO> getRentalsByClientId(@PathVariable (name = "id") String id) {

        RentalsBookCategoriesClientDTO rentalInfoByClientId = rentalService.getRentalInfoByClientId(Long.parseLong(id));
        return ResponseEntity.ok(rentalInfoByClientId);

    }

    @PostMapping
    public ResponseEntity<RentalDTO> createRental (@Valid @RequestBody RentalDTO rental) {

        RentalDTO rentalDTO = rentalService.orderNewRental(rental);
        HttpStatus status = HttpStatus.CREATED;
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(rentalDTO.getClientId()).toUri();
        return ResponseEntity.created(location).body(rentalDTO);

    }


}
