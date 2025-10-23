package org.ms.library.catalog.service;

import org.ms.library.catalog.dto.BookCategoriesDTO;
import org.ms.library.catalog.dto.BookDTO;
import org.ms.library.catalog.entity.Book;
import org.ms.library.catalog.entity.Category;
import org.ms.library.catalog.repository.BookRepository;
import org.ms.library.catalog.repository.CategoryRepository;
import org.ms.library.catalog.repository.projection.BookCategoriesProjection;
import org.ms.library.catalog.service.exceptions.NoBookFoundException;
import org.ms.library.catalog.service.exceptions.NoCategoryFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);


    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }


    @Transactional(readOnly = true)
    public Page<BookDTO> getAllBooksPaged(Pageable pageable) {

        Page<Book> allBooksPaged = bookRepository.findAll(pageable);

        return allBooksPaged.map(BookDTO::new);

    }

    @Transactional(readOnly = true)
    public BookCategoriesDTO getBookCategoriesByBookID(Long id) {
        Book book = bookRepository.findBookCategoriesByBookID(id).orElseThrow(() -> {

            logger.error("No book found with id {}", id);
            return new NoBookFoundException("No book found with id: " + id);

        });
        return new BookCategoriesDTO(book);

    }

    @Transactional
    public BookCategoriesDTO createBook(BookCategoriesDTO bookDTO) {

        for (Long id : bookDTO.getCategories_ids()) {

            if (!categoryRepository.existsById(id)) {

                logger.error("Category not found with id {}", id);
                throw new NoCategoryFoundException("No category found with id: " + id);

            }

        }
        Book book = new Book();
        DTOtoEntity(bookDTO, book);
        Book persistedEntity = bookRepository.save(book);
        logger.info("Added new book with id {}", persistedEntity.getId());
        return new BookCategoriesDTO(book);
    }


    @Transactional
    public BookCategoriesDTO updateBookCategories(Long id, BookCategoriesDTO bookDTO) {

        if (!bookRepository.existsById(id)) {

            logger.error("Book not found with id {}", id);
            throw new NoBookFoundException("No book found with this id: " + id);
        }

        Book referenceById = bookRepository.getReferenceById(id);
        DTOtoEntity(bookDTO, referenceById);

        bookRepository.save(referenceById);
        logger.info("Updated book successfully");
        return new BookCategoriesDTO(referenceById);


    }


    @Transactional(readOnly = true)
    public Page<BookCategoriesProjection> getAllBooksAndCategories(String title, String author, Set<String> category, Pageable pageable) {


        Optional<Page<BookCategoriesProjection>> books = bookRepository.findAllBooksCategories(title, author, category, pageable);

        return books.orElseThrow(() -> {
            logger.error("No books found with title {} and author {} and category {}", title, author, category);
            return new NoBookFoundException("No books found with title: " + title + ", author: " + author + ", category: " + category);
        });

    }


    @Transactional
    public BookCategoriesDTO changeStockAmount(Long id, Integer amount, String operation) {

        if (!bookRepository.existsById(id)) {

            logger.error("Book not found with id {}", id);
            throw new NoBookFoundException("No book found with this id: " + id);

        }

        Book referenceById = bookRepository.getReferenceById(id);


        if (operation.equalsIgnoreCase("INCREASE")) {

            referenceById.setQuantity(referenceById.getQuantity() + amount);
            logger.info("Updated stock amount successfully");
        } else if (operation.equalsIgnoreCase("DECREASE")) {

            referenceById.setQuantity(referenceById.getQuantity() - amount);
            logger.info("Updated stock amount successfully");

        } else {

            logger.error("Operation not supported");
            throw new IllegalArgumentException("Invalid operation!");

        }

        return new BookCategoriesDTO(bookRepository.save(referenceById));


    }


    private void DTOtoEntity(BookCategoriesDTO bookDTO, Book book) {

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPrice(bookDTO.getPrice());
        book.setQuantity(bookDTO.getQuantity());

        for (Long dto : bookDTO.getCategories_ids()) {
            Category c = categoryRepository.findById(dto).get();
            book.getCategories().add(c);
        }


    }


    @Transactional
    public void deleteBookById(Long id) {

        if (!bookRepository.existsById(id)) {

            logger.error("Book not found with id {}", id);
            throw new NoBookFoundException("No book found with this id: " + id);
        }

        bookRepository.deleteById(id);

    }

    @Transactional (readOnly = true)
    public Set<BookCategoriesDTO> getBooksByListOfIds (Set<Long> ids) {

        Set<Book> booksByListOfIds = bookRepository.findBooksCategoriesByListOfIds(ids);

        return booksByListOfIds.stream().map(BookCategoriesDTO::new).collect(Collectors.toSet());

    }
}
