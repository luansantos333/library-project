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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;


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
        Book book = bookRepository.findBookCategoriesByBookID(id).orElseThrow(() -> new NoBookFoundException("No book found with id: " + id));
        return new BookCategoriesDTO(book);

    }

    @Transactional
    public BookCategoriesDTO createBook(BookCategoriesDTO bookDTO) {

        for (Long id : bookDTO.getCategories_ids()) {

            if (!categoryRepository.existsById(id)) {

                throw new NoCategoryFoundException("No category found with id: " + id);

            }

        }
        Book book = new Book();
        DTOtoEntity(bookDTO, book);
        bookRepository.save(book);
        return new BookCategoriesDTO(book);
    }


    @Transactional
    public BookCategoriesDTO updateBookCategories(Long id, BookCategoriesDTO bookDTO) {

        if (!bookRepository.existsById(id)) {

            throw new NoBookFoundException("No book found with this id: " + id);
        }

        Book referenceById = bookRepository.getReferenceById(id);
        DTOtoEntity(bookDTO, referenceById);

        bookRepository.save(referenceById);
        return new BookCategoriesDTO(referenceById);


    }


    @Transactional(readOnly = true)
    public Page<BookCategoriesProjection> getAllBooksAndCategories(String title, String author, Set<String> category, Pageable pageable) {


        Optional<Page<BookCategoriesProjection>> books = bookRepository.findAllBooksCategories(title, author, category, pageable);

        return books.orElseThrow(() -> new NoBookFoundException("No books found with title: " + title + ", author: " + author + ", category: " + category));

    }


    @Transactional
    public BookCategoriesDTO changeStockAmount(Long id, Integer amount, String operation) {

        if (!bookRepository.existsById(id)) {
            throw new NoBookFoundException("No book found with this id: " + id);

        }

        Book referenceById = bookRepository.getReferenceById(id);


        if (operation.equalsIgnoreCase("INCREASE")) {

            referenceById.setQuantity(referenceById.getQuantity() + amount);
        } else if (operation.equalsIgnoreCase("DECREASE")) {

            referenceById.setQuantity(referenceById.getQuantity() - amount);

        } else {


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
