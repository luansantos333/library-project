package org.ms.library.rental.controller;

import jakarta.validation.Valid;
import org.ms.library.rental.dto.LoanDTO;
import org.ms.library.rental.dto.RentalsBookCategoriesClientDTO;
import org.ms.library.rental.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<RentalsBookCategoriesClientDTO> getRentalsByClientId(@PathVariable (name = "id") String id, Authentication authentication) {

        RentalsBookCategoriesClientDTO rentalInfoByClientId = loanService.getLoanInfoByClientId(Long.parseLong(id), authentication);
        if (rentalInfoByClientId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(rentalInfoByClientId);

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<LoanDTO> createRental (@Valid @RequestBody LoanDTO rental) {

        LoanDTO loanDTO = loanService.orderNewLoan(rental);
        HttpStatus status = HttpStatus.CREATED;
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(loanDTO.getClientId()).toUri();
        return ResponseEntity.created(location).body(loanDTO);

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/return/{id}/{clientId}")
    public ResponseEntity<Void>  returnBook (@PathVariable (name = "id") UUID rentalId,  @PathVariable (name = "clientId") Long clientId) {

        try {

            loanService.returnBooks(rentalId, clientId);

        } catch (Exception ex) {

            ex.printStackTrace();


        }

        return ResponseEntity.noContent().build();


    }


}
