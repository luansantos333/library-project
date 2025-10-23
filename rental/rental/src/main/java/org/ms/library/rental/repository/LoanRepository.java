package org.ms.library.rental.repository;

import org.ms.library.rental.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {


    @Query (value = "SELECT l FROM Loan l WHERE l.clientId = :clientId")
    Optional<List<Loan>> findLoansByClientId(@Param("clientId") Long clientId);

    @Query
    Optional<Loan> findLoanById(@Param("clientId") UUID id);

}
