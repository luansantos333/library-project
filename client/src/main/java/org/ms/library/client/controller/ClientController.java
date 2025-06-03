package org.ms.library.client.controller;

import org.ms.library.client.dto.ClientDTO;
import org.ms.library.client.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}
