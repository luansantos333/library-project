package org.ms.library.rental.repository;

import org.ms.library.rental.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {


    @Query (value = "SELECT r FROM tb_rental r WHERE r.clientId = :clientId")
    Optional<List<Rental>> findRentalsByClientId (@Param("clientId") Long clientId);



}
