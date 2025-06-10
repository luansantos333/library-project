package org.ms.library.client.service;

import org.ms.library.client.dto.ClientAddressDTO;
import org.ms.library.client.dto.ClientDTO;
import org.ms.library.client.entity.Client;
import org.ms.library.client.repository.ClientRepository;
import org.ms.library.client.repository.projections.ClientAddressProjection;
import org.ms.library.client.service.exceptions.ClientNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Transactional(readOnly = true)
    public ClientDTO findClientById(Long id) {

        Optional<Client> client = clientRepository.findById(id);

        if (client.isPresent()) {


            return new ClientDTO(client.get());

        } else throw new ClientNotFoundException("Client not found with id: " + id);

    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findClient(String name, String cpf, Pageable pageable) {

        Page<Client> byName = clientRepository.findByName(name, cpf, pageable);
        return byName.map(x -> new ClientDTO(x));

    }


    @Transactional(readOnly = true)
    public ClientAddressDTO findClientAddressById(Long id) {


        Client client = clientRepository.findAddressAndClientById(id).orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id));

        return new ClientAddressDTO(client);

    }

    @Transactional(readOnly = true)
    public Page<ClientAddressProjection> findClientsAndAdressesByNameOrCPF(Pageable pageable, String name, String cpf) {

        Page<ClientAddressProjection> clientAddressProjections = clientRepository.searchClientAddressByClientNameOrCpf(pageable, name, cpf);

        return clientAddressProjections;

    }
}
