package org.ms.library.rental.controller;

import org.ms.library.rental.dto.RentalDTO;
import org.ms.library.rental.dto.RentalsBookClientDTO;
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
    public ResponseEntity<RentalsBookClientDTO> getRentalsByClientId(@PathVariable (name = "id") String id) {

        RentalsBookClientDTO rentalInfoByClientId = rentalService.getRentalInfoByClientId(Long.parseLong(id));
        return ResponseEntity.ok(rentalInfoByClientId);

    }

    @PostMapping
    public ResponseEntity<RentalDTO> newRental(@RequestBody RentalDTO rental) {

        RentalDTO rentalDTO = rentalService.orderNewRental(rental);
        HttpStatus status = HttpStatus.CREATED;
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(rentalDTO.getClientId()).toUri();
        return ResponseEntity.created(location).body(rentalDTO);

    }


}
