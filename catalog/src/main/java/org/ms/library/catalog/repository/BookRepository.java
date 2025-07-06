package org.ms.library.catalog.repository;

import org.ms.library.catalog.entity.Book;
import org.ms.library.catalog.repository.projection.BookCategoriesProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT b FROM tb_book b")
    Page<Book> findAllBooksPaged(Pageable pageable);

    @Query (value = "SELECT b.author AS author, b.title AS title, b.price AS price, c.name AS categoryName FROM tb_book b JOIN b.categories c WHERE " +
            "UPPER(b.title) LIKE (UPPER(CONCAT('%', :title, '%'))) " +
            "AND (:author IS NULL OR UPPER(b.author) LIKE (UPPER(CONCAT('%', :author, '%')))) AND (:categories IS NULL OR c.name IN (:categories))",
            countQuery = "SELECT COUNT (DISTINCT b.id) FROM tb_book b JOIN b.categories c WHERE UPPER(b.title) LIKE (UPPER(CONCAT('%', :title, '%'))) " +
                    "AND (:author IS NULL OR UPPER(b.author) LIKE (UPPER(CONCAT('%', :author, '%')))) AND (:categories IS NULL OR c.name IN (:categories))" )
    Optional<Page<BookCategoriesProjection>> findAllBooksCategories(@Param("title") String title, @Param("author") String author, @Param ("categories") Set<String> categories, Pageable pageable);

    @Query (value = "SELECT book FROM tb_book book WHERE book.id IN (:ids)")
    List<Book> findBooksByListOfIds (@Param("ids") List<Long> ids);


}
