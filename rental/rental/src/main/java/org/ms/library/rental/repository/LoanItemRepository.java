package org.ms.library.rental.repository;

import org.ms.library.rental.entities.LoanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanItemRepository extends JpaRepository<LoanItem, Long> {
}
