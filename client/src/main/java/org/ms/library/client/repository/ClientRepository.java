package org.ms.library.client.repository;

import org.ms.library.client.entity.Client;
import org.ms.library.client.repository.projections.ClientAddressProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value = "SELECT c FROM tb_client c WHERE c.name = :name AND (:cpf IS NULL OR c.cpf LIKE (CONCAT('%',:cpf, '%')))")
    Page<Client> findByName(String name, String cpf, Pageable pageable);
    @Query (value = "SELECT c FROM tb_client c JOIN FETCH c.address WHERE c.id = :id" )
    Optional<Client> findAddressAndClientById (Long id);
    @Query(nativeQuery = true, value = "SELECT client.name, client.last_name AS lastName, client.cpf, client.phone, address.address, address.city, " +
            " address.country, address.zip, address.state FROM tb_client AS client INNER JOIN tb_address AS address ON client.address_id = address.client_id", countQuery = "SELECT COUNT(client.id) FROM tb_client" +
            " AS client INNER JOIN tb_address AS address")
    Page<ClientAddressProjection> searchClientAddressByClientNameOrCpf(Pageable p, String name, String cpf);

}
