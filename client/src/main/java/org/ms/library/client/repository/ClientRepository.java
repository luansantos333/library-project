package org.ms.library.client.repository;

import org.ms.library.client.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query (value = "SELECT c FROM tb_client c WHERE c.name = :name OR c.cpf = :cpf ")
    Page<Client> findByName(String name, String cpf, Pageable pageable);

}
