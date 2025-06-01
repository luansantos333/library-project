package org.ms.library.catalog.service;

import org.ms.library.catalog.dto.BookCategoriesDTO;
import org.ms.library.catalog.dto.BookDTO;
import org.ms.library.catalog.entity.Book;
import org.ms.library.catalog.entity.Category;
import org.ms.library.catalog.repository.BookRepository;
import org.ms.library.catalog.repository.CategoryRepository;
import org.ms.library.catalog.service.exceptions.NoBookFoundException;
import org.ms.library.catalog.service.exceptions.NoCategoryFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;


    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<BookDTO> getAllBooksPaged(Pageable pageable) {

        Page<Book> allBooksPaged = bookRepository.findAll(pageable);

        return allBooksPaged.map(BookDTO::new);

    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NoBookFoundException("No book found with id: " + id));
        return new BookDTO(book);

    }

    public BookCategoriesDTO createBook (BookCategoriesDTO bookDTO) {

        for (Long id  : bookDTO.getCategories_ids()) {

            if (!categoryRepository.existsById(id)) {

                throw new NoCategoryFoundException("No category found with id: " + id);

            }

        }
        Book book = new Book();
        DTOtoEntity(bookDTO, book);
        bookRepository.save(book);
        return new BookCategoriesDTO(book);
    }

    private void DTOtoEntity(BookCategoriesDTO bookDTO, Book book) {

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPrice(bookDTO.getPrice());

        for (Long dto : bookDTO.getCategories_ids()) {
            Category c = categoryRepository.findById(dto).get();
            book.getCategories().add(c);
        }


    }


}
