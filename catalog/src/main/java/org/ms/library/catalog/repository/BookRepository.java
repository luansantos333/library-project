package org.ms.library.catalog.repository;

import org.ms.library.catalog.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT b FROM tb_book b")
    Page<Book> findAllBooksPaged(Pageable pageable);


}
