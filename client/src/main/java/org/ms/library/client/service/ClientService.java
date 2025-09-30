package org.ms.library.client.service;

import org.ms.library.client.dto.*;
import org.ms.library.client.entity.Address;
import org.ms.library.client.entity.Client;
import org.ms.library.client.feign.FeignClients;
import org.ms.library.client.repository.AddressRepository;
import org.ms.library.client.repository.ClientRepository;
import org.ms.library.client.repository.projections.ClientAddressProjection;
import org.ms.library.client.service.exceptions.ClientNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@EnableWebSecurity
public class ClientService {

    private final ClientRepository clientRepository;
    private final FeignClients feignClient;
    private final AddressRepository addressRepository;


    public ClientService(ClientRepository clientRepository, FeignClients feignClient, AddressRepository addressRepository) {
        this.clientRepository = clientRepository;
        this.feignClient = feignClient;
        this.addressRepository = addressRepository;
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

        return clientRepository.searchClientAddressByClientNameOrCpf(pageable, name, cpf);

    }


    @Transactional
    public ClientAddressUserDTO createClientAddress(ClientAddressUserDTO clientAddressUserDTO) throws Exception {


        Client client = new Client();
        Address address = new Address();
        UserDTO user = createUser(clientAddressUserDTO);
        copyDTOContentToEntity(clientAddressUserDTO, client, user, address);
        Client savedClient = clientRepository.save(client);
        return new ClientAddressUserDTO(savedClient, new UserCompleteDTO(user.getUsername(), user.getPassword()));

    }


    public UserDTO createUser(ClientAddressUserDTO clientAddressUserDTO) {

        UserDTO userDTO = new UserDTO(clientAddressUserDTO.getUser().getUsername(), clientAddressUserDTO.getUser().getPassword());

        ResponseEntity<UserDTO> user = feignClient.createUser(userDTO);

        return user.getBody();

    }


    private void copyDTOContentToEntity(ClientAddressUserDTO clientDTO, Client entity, UserDTO userDTO, Address address) {

        entity.setName(clientDTO.getName());
        entity.setCpf(clientDTO.getCpf().replaceAll("\\D", ""));
        address.setCity(clientDTO.getAddress().getCity());
        address.setState(clientDTO.getAddress().getState());
        address.setCountry(clientDTO.getAddress().getCountry());
        address.setZip(clientDTO.getAddress().getZip().replaceAll("\\D", ""));
        address.setAddress(clientDTO.getAddress().getAddress());
        address.setClient(entity);
        Address savedAddress = addressRepository.save(address);
        entity.setLastName(clientDTO.getLastName());
        entity.setPhone(clientDTO.getPhone().replaceAll("\\D", ""));
        entity.setAddress(savedAddress);
        entity.setUser_id(userDTO.getId());

    }

    public boolean validateIfLoggedUserIsAdminOrOwner(Long clientId, Authentication authentication) {

        if (authentication == null) {
            return false;
        }

        // CHECK IF USER HAS ROLE ADMIN
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        if (jwtAuthenticationToken.getTokenAttributes().get("roles").toString().contains("ROLE_ADMIN")) {

            return true;
        }


        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ClientNotFoundException("No client found with this id!"));
        UserDTO body = feignClient.findUserById(client.getUser_id().toString()).getBody();


        // CHECK IF USER IS THE OWNER OF THE RESOURCE
        if (jwtAuthenticationToken.getTokenAttributes().get("username").equals(body.getUsername())) {

            return true;

        }


        return false;

    }

}
