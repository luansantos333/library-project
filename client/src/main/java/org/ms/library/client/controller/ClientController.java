package org.ms.library.client.controller;

import jakarta.validation.Valid;
import org.ms.library.client.dto.ClientAddressDTO;
import org.ms.library.client.dto.ClientAddressUserDTO;
import org.ms.library.client.dto.ClientDTO;
import org.ms.library.client.repository.projections.ClientAddressProjection;
import org.ms.library.client.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable("id") Long id, Authentication authentication) {

        boolean validateIfLoggedUserIsAdminOrOwner = clientService.validateIfLoggedUserIsAdminOrOwner(id, authentication);

        if (!validateIfLoggedUserIsAdminOrOwner) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        }
        ClientDTO clientById = clientService.findClientById(id);
        return ResponseEntity.ok(clientById);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<ClientDTO>> findClient(@RequestParam(required = true, name = "name")
                                      String name, @RequestParam(required = false, name = "cpf") String cpf,
                                      Pageable pageable) {

        Page<ClientDTO> client = clientService.findClient(name, cpf, pageable);


        return ResponseEntity.ok(client);

    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping ("/address/{id}")
    public ResponseEntity<ClientAddressDTO> findClientAndAddressByID(@PathVariable(name = "id") Long id, Authentication authentication ) {


        boolean validateIfLoggedUserIsAdminOrOwner = clientService.validateIfLoggedUserIsAdminOrOwner(id, authentication);

        if (!validateIfLoggedUserIsAdminOrOwner) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        }

        ClientAddressDTO clientAddressById = clientService.findClientAddressById(id);

        return ResponseEntity.ok(clientAddressById);

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping ("/address")
    public ResponseEntity<Page<ClientAddressProjection>> findClientsAndAddressesByNameOrCPF (Pageable p, @RequestParam (name = "name", required = false) String name,
                                                                                             @RequestParam (name = "cpf", required = false) String cpf) {


        Page<ClientAddressProjection> clientsAndAdressesByNameOrCPF = clientService.findClientsAndAdressesByNameOrCPF(p, name, cpf);


        return ResponseEntity.ok(clientsAndAdressesByNameOrCPF);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ClientDTO> createClientAndUser(@Valid @RequestBody ClientAddressUserDTO clientDTO) throws Exception {

        ClientAddressUserDTO clientAddress = clientService.createClientAddress(clientDTO);
        HttpStatus status = HttpStatus.CREATED;
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clientAddress.getId()).toUri();

        return ResponseEntity.status(status).location(location).build();


    }


}
