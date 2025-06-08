package org.ms.library.client.repository;

import org.ms.library.client.entity.Client;
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

}
