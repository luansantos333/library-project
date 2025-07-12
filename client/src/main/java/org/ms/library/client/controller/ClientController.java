package org.ms.library.client.controller;

import org.ms.library.client.dto.ClientAddressDTO;
import org.ms.library.client.dto.ClientAddressUserDTO;
import org.ms.library.client.dto.ClientDTO;
import org.ms.library.client.repository.projections.ClientAddressProjection;
import org.ms.library.client.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable("id") Long id) {

        ClientDTO clientById = clientService.findClientById(id);
        return ResponseEntity.ok(clientById);
    }

    @GetMapping
    public ResponseEntity<Page<ClientDTO>> findClient(@RequestParam(required = true, name = "name")
                                      String name, @RequestParam(required = false, name = "cpf") String cpf,
                                      Pageable pageable) {

        Page<ClientDTO> client = clientService.findClient(name, cpf, pageable);


        return ResponseEntity.ok(client);

    }


    @GetMapping ("/address/{id}")
    public ResponseEntity<ClientAddressDTO> findClientAndAddressByID(@PathVariable(name = "id") Long id) {

        ClientAddressDTO clientAddressById = clientService.findClientAddressById(id);

        return ResponseEntity.ok(clientAddressById);

    }

    @GetMapping ("/address")
    public ResponseEntity<Page<ClientAddressProjection>> findClientsAndAddressesByNameOrCPF (Pageable p, @RequestParam (name = "name", required = false) String name,
                                                                                             @RequestParam (name = "cpf", required = false) String cpf) {


        Page<ClientAddressProjection> clientsAndAdressesByNameOrCPF = clientService.findClientsAndAdressesByNameOrCPF(p, name, cpf);


        return ResponseEntity.ok(clientsAndAdressesByNameOrCPF);

    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClientAndUser(@RequestBody ClientAddressUserDTO clientDTO) {

        ClientAddressUserDTO clientAddress = clientService.createClientAddress(clientDTO);
        HttpStatus status = HttpStatus.CREATED;
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clientAddress.getId()).toUri();

        return ResponseEntity.status(status).location(location).build();


    }


}
